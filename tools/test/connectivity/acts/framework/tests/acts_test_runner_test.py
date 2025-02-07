#!/usr/bin/env python3.4
#
#   Copyright 2016 - The Android Open Source Project
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

import mock
import shutil
import tempfile
import unittest

from acts import keys
from acts import signals
from acts import test_runner

import acts_android_device_test
import mock_controller


class ActsTestRunnerTest(unittest.TestCase):
    """This test class has unit tests for the implementation of everything
    under acts.test_runner.
    """

    def setUp(self):
        self.tmp_dir = tempfile.mkdtemp()
        self.base_mock_test_config = {
            "testbed": {
                "name": "SampleTestBed",
            },
            "logpath": self.tmp_dir,
            "cli_args": None,
            "testpaths": ["./"],
            "icecream": 42,
            "extra_param": "haha"
        }
        self.mock_run_list = [('SampleTest', None)]

    def tearDown(self):
        shutil.rmtree(self.tmp_dir)

    def test_register_controller_no_config(self):
        tr = test_runner.TestRunner(self.base_mock_test_config,
                                    self.mock_run_list)
        with self.assertRaisesRegexp(signals.ControllerError,
                                     "No corresponding config found for"):
            tr.register_controller(mock_controller)

    def test_register_optional_controller_no_config(self):
        tr = test_runner.TestRunner(self.base_mock_test_config,
                                    self.mock_run_list)
        self.assertIsNone(
            tr.register_controller(
                mock_controller, required=False))

    def test_register_controller_third_party_dup_register(self):
        """Verifies correctness of registration, internal tally of controllers
        objects, and the right error happen when a controller module is
        registered twice.
        """
        mock_test_config = dict(self.base_mock_test_config)
        tb_key = keys.Config.key_testbed.value
        mock_ctrlr_config_name = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
        mock_test_config[tb_key][mock_ctrlr_config_name] = ["magic1", "magic2"]
        tr = test_runner.TestRunner(mock_test_config, self.mock_run_list)
        tr.register_controller(mock_controller)
        registered_name = "mock_controller"
        self.assertTrue(registered_name in tr.controller_registry)
        mock_ctrlrs = tr.controller_registry[registered_name]
        self.assertEqual(mock_ctrlrs[0].magic, "magic1")
        self.assertEqual(mock_ctrlrs[1].magic, "magic2")
        self.assertTrue(tr.controller_destructors[registered_name])
        expected_msg = "Controller module .* has already been registered."
        with self.assertRaisesRegexp(signals.ControllerError, expected_msg):
            tr.register_controller(mock_controller)

    def test_register_optional_controller_third_party_dup_register(self):
        """Verifies correctness of registration, internal tally of controllers
        objects, and the right error happen when an optional controller module
        is registered twice.
        """
        mock_test_config = dict(self.base_mock_test_config)
        tb_key = keys.Config.key_testbed.value
        mock_ctrlr_config_name = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
        mock_test_config[tb_key][mock_ctrlr_config_name] = ["magic1", "magic2"]
        tr = test_runner.TestRunner(mock_test_config, self.mock_run_list)
        tr.register_controller(mock_controller, required=False)
        expected_msg = "Controller module .* has already been registered."
        with self.assertRaisesRegexp(signals.ControllerError, expected_msg):
            tr.register_controller(mock_controller, required=False)

    def test_register_controller_builtin_dup_register(self):
        """Same as test_register_controller_third_party_dup_register, except
        this is for a builtin controller module.
        """
        mock_test_config = dict(self.base_mock_test_config)
        tb_key = keys.Config.key_testbed.value
        mock_ctrlr_config_name = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
        mock_ref_name = "haha"
        setattr(mock_controller, "ACTS_CONTROLLER_REFERENCE_NAME",
                mock_ref_name)
        try:
            mock_ctrlr_ref_name = mock_controller.ACTS_CONTROLLER_REFERENCE_NAME
            mock_test_config[tb_key][mock_ctrlr_config_name] = ["magic1",
                                                                "magic2"]
            tr = test_runner.TestRunner(mock_test_config, self.mock_run_list)
            tr.register_controller(mock_controller)
            self.assertTrue(mock_ref_name in tr.test_run_info)
            self.assertTrue(mock_ref_name in tr.controller_registry)
            mock_ctrlrs = tr.test_run_info[mock_ctrlr_ref_name]
            self.assertEqual(mock_ctrlrs[0].magic, "magic1")
            self.assertEqual(mock_ctrlrs[1].magic, "magic2")
            self.assertTrue(tr.controller_destructors[mock_ctrlr_ref_name])
            expected_msg = "Controller module .* has already been registered."
            with self.assertRaisesRegexp(signals.ControllerError,
                                         expected_msg):
                tr.register_controller(mock_controller)
        finally:
            delattr(mock_controller, "ACTS_CONTROLLER_REFERENCE_NAME")

    def test_register_controller_no_get_info(self):
        mock_test_config = dict(self.base_mock_test_config)
        tb_key = keys.Config.key_testbed.value
        mock_ctrlr_config_name = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
        mock_ref_name = "haha"
        get_info = getattr(mock_controller, "get_info")
        delattr(mock_controller, "get_info")
        try:
            mock_test_config[tb_key][mock_ctrlr_config_name] = ["magic1",
                                                                "magic2"]
            tr = test_runner.TestRunner(mock_test_config, self.mock_run_list)
            tr.register_controller(mock_controller)
            self.assertEqual(tr.results.controller_info, {})
        finally:
            setattr(mock_controller, "get_info", get_info)

    def test_register_controller_return_value(self):
        mock_test_config = dict(self.base_mock_test_config)
        tb_key = keys.Config.key_testbed.value
        mock_ctrlr_config_name = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
        mock_test_config[tb_key][mock_ctrlr_config_name] = ["magic1", "magic2"]
        tr = test_runner.TestRunner(mock_test_config, self.mock_run_list)
        magic_devices = tr.register_controller(mock_controller)
        self.assertEqual(magic_devices[0].magic, "magic1")
        self.assertEqual(magic_devices[1].magic, "magic2")

    def test_run_twice(self):
        """Verifies that:
        1. Repeated run works properly.
        2. The original configuration is not altered if a test controller
           module modifies configuration.
        """
        mock_test_config = dict(self.base_mock_test_config)
        tb_key = keys.Config.key_testbed.value
        mock_ctrlr_config_name = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
        my_config = [{"serial": "xxxx",
                      "magic": "Magic1"}, {"serial": "xxxx",
                                           "magic": "Magic2"}]
        mock_test_config[tb_key][mock_ctrlr_config_name] = my_config
        tr = test_runner.TestRunner(mock_test_config, [('IntegrationTest',
                                                        None)])
        tr.run()
        self.assertFalse(tr.controller_registry)
        self.assertFalse(tr.controller_destructors)
        self.assertTrue(mock_test_config[tb_key][mock_ctrlr_config_name][0])
        tr.run()
        tr.stop()
        self.assertFalse(tr.controller_registry)
        self.assertFalse(tr.controller_destructors)
        results = tr.results.summary_dict()
        self.assertEqual(results["Requested"], 2)
        self.assertEqual(results["Executed"], 2)
        self.assertEqual(results["Passed"], 2)
        expected_info = {'MagicDevice': [{'MyMagic': {'magic': 'Magic1'}},
                                         {'MyMagic': {'magic': 'Magic2'}}]}
        self.assertEqual(tr.results.controller_info, expected_info)

    @mock.patch(
        'acts.controllers.adb.AdbProxy',
        return_value=acts_android_device_test.MockAdbProxy(1))
    @mock.patch(
        'acts.controllers.fastboot.FastbootProxy',
        return_value=acts_android_device_test.MockFastbootProxy(1))
    @mock.patch(
        'acts.controllers.android_device.list_adb_devices', return_value=["1"])
    @mock.patch(
        'acts.controllers.android_device.get_all_instances',
        return_value=acts_android_device_test.get_mock_ads(1))
    def test_run_two_test_classes(self,
                                  mock_get_all,
                                  mock_list_adb,
                                  mock_fastboot,
                                  mock_adb, ):
        """Verifies that runing more than one test class in one test run works
        proerly.

        This requires using a built-in controller module. Using AndroidDevice
        module since it has all the mocks needed already.
        """
        mock_test_config = dict(self.base_mock_test_config)
        tb_key = keys.Config.key_testbed.value
        mock_ctrlr_config_name = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
        my_config = [{"serial": "xxxx",
                      "magic": "Magic1"}, {"serial": "xxxx",
                                           "magic": "Magic2"}]
        mock_test_config[tb_key][mock_ctrlr_config_name] = my_config
        mock_test_config[tb_key]["AndroidDevice"] = [
            {"serial": "1", "skip_sl4a": True}]
        tr = test_runner.TestRunner(mock_test_config,
            [('IntegrationTest', None), ('IntegrationTest', None)])
        tr.run()
        tr.stop()
        self.assertFalse(tr.controller_registry)
        self.assertFalse(tr.controller_destructors)
        results = tr.results.summary_dict()
        self.assertEqual(results["Requested"], 2)
        self.assertEqual(results["Executed"], 2)
        self.assertEqual(results["Passed"], 2)

    def test_verify_controller_module(self):
        test_runner.TestRunner.verify_controller_module(mock_controller)

    def test_verify_controller_module_null_attr(self):
        try:
            tmp = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
            mock_controller.ACTS_CONTROLLER_CONFIG_NAME = None
            msg = "Controller interface .* in .* cannot be null."
            with self.assertRaisesRegexp(signals.ControllerError, msg):
                test_runner.TestRunner.verify_controller_module(
                    mock_controller)
        finally:
            mock_controller.ACTS_CONTROLLER_CONFIG_NAME = tmp

    def test_verify_controller_module_missing_attr(self):
        try:
            tmp = mock_controller.ACTS_CONTROLLER_CONFIG_NAME
            delattr(mock_controller, "ACTS_CONTROLLER_CONFIG_NAME")
            msg = "Module .* missing required controller module attribute"
            with self.assertRaisesRegexp(signals.ControllerError, msg):
                test_runner.TestRunner.verify_controller_module(
                    mock_controller)
        finally:
            setattr(mock_controller, "ACTS_CONTROLLER_CONFIG_NAME", tmp)


if __name__ == "__main__":
    unittest.main()
