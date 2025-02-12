#!/usr/bin/env python3.4
#
#   Copyright 2017 - Google
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
"""
    Base Class for Defining Common WiFi Test Functionality
"""

from acts import asserts
from acts import utils
from acts.base_test import BaseTestClass
from acts.signals import TestSignal
from acts.controllers import android_device
from acts.controllers.ap_lib import hostapd_ap_preset
from acts.controllers.ap_lib import hostapd_bss_settings
from acts.controllers.ap_lib import hostapd_constants
from acts.controllers.ap_lib import hostapd_security


class WifiBaseTest(BaseTestClass):
    def __init__(self, controllers):
        BaseTestClass.__init__(self, controllers)


    def get_wpa2_network(
            self,
            ap_count=1,
            ssid_length_2g=hostapd_constants.AP_SSID_LENGTH_2G,
            ssid_length_5g=hostapd_constants.AP_SSID_LENGTH_5G,
            passphrase_length_2g=hostapd_constants.AP_PASSPHRASE_LENGTH_2G,
            passphrase_length_5g=hostapd_constants.AP_PASSPHRASE_LENGTH_5G):

        """Generates SSID and passphrase for a WPA2 network using random
           generator.

           Args:
               ap_count: Determines if we want to use one or both the APs
                         for configuration. If set to '2', then both APs will
                         be configured with the same configuration.
               ssid_length_2g: Int, number of characters to use for 2G SSID.
               ssid_length_5g: Int, number of characters to use for 5G SSID.
               passphrase_length_2g: Int, length of password for 2G network.
               passphrase_length_5g: Int, length of password for 5G network.
           Returns: A dict of 2G and 5G network lists for hostapd configuration.

        """
        network_dict_2g = {}
        network_dict_5g = {}
        ref_5g_security = hostapd_constants.WPA2_STRING
        ref_2g_security = hostapd_constants.WPA2_STRING
        self.user_params["reference_networks"] = []

        ref_2g_ssid = '2g_%s' % utils.rand_ascii_str(ssid_length_2g)
        ref_2g_passphrase = utils.rand_ascii_str(passphrase_length_2g)

        ref_5g_ssid = '5g_%s' % utils.rand_ascii_str(ssid_length_5g)
        ref_5g_passphrase = utils.rand_ascii_str(passphrase_length_5g)

        network_dict_2g = {
            "SSID": ref_2g_ssid,
            "security": ref_2g_security,
            "password": ref_2g_passphrase
        }

        network_dict_5g = {
            "SSID": ref_5g_ssid,
            "security": ref_5g_security,
            "password": ref_5g_passphrase
        }

        for ap in range(ap_count):
            self.user_params["reference_networks"].append({
                "2g": network_dict_2g,
                "5g": network_dict_5g
            })
        self.reference_networks = self.user_params["reference_networks"]
        return {"2g": network_dict_2g, "5g": network_dict_5g}


    def get_open_network(
            self,
            ap_count=1,
            ssid_length_2g=hostapd_constants.AP_SSID_LENGTH_2G,
            ssid_length_5g=hostapd_constants.AP_SSID_LENGTH_5G):

        """Generates SSIDs for a open network using a random generator.

        Args:
            ap_count: Determines if we want to use one or both the APs for
                      for configuration. If set to '2', then both APs will
                      be configured with the same configuration.
            ssid_length_2g: Int, number of characters to use for 2G SSID.
            ssid_length_5g: Int, number of characters to use for 5G SSID.
        Returns: A dict of 2G and 5G network lists for hostapd configuration.

        """
        network_dict_2g = {}
        network_dict_5g = {}
        self.user_params["open_network"] = []
        open_2g_ssid = '2g_%s' % utils.rand_ascii_str(ssid_length_2g)
        open_5g_ssid = '5g_%s' % utils.rand_ascii_str(ssid_length_5g)
        network_dict_2g = {"SSID": open_2g_ssid, "security": 'none'}
        network_dict_5g = {"SSID": open_5g_ssid, "security": 'none'}
        for ap in range(ap_count):
            self.user_params["open_network"].append({
                "2g": network_dict_2g,
                "5g": network_dict_5g
            })
        self.open_networks = self.user_params["open_network"]
        return {"2g": network_dict_2g, "5g": network_dict_5g}


    def legacy_configure_ap_and_start(
            self,
            channel_5g=hostapd_constants.AP_DEFAULT_CHANNEL_5G,
            channel_2g=hostapd_constants.AP_DEFAULT_CHANNEL_2G,
            max_2g_networks=hostapd_constants.AP_DEFAULT_MAX_SSIDS_2G,
            max_5g_networks=hostapd_constants.AP_DEFAULT_MAX_SSIDS_5G,
            ap_ssid_length_2g=hostapd_constants.AP_SSID_LENGTH_2G,
            ap_passphrase_length_2g=hostapd_constants.AP_PASSPHRASE_LENGTH_2G,
            ap_ssid_length_5g=hostapd_constants.AP_SSID_LENGTH_5G,
            ap_passphrase_length_5g=hostapd_constants.AP_PASSPHRASE_LENGTH_5G,
            ap_count=1):
        asserts.assert_true(
            len(self.user_params["AccessPoint"]) == 2,
            "Exactly two access points must be specified. \
             Each accesspoint has 2 radios, one each for 2.4GHZ \
             and 5GHz. A test can choose to use one or both APs.")
        network_list_2g = []
        network_list_5g = []
        network_list_2g.append({"channel": channel_2g})
        network_list_5g.append({"channel": channel_5g})

        if "reference_networks" in self.user_params:
            pass
        else:
            networks_dict = self.get_wpa2_network(ap_count=ap_count)
            network_list_2g.append(networks_dict["2g"])
            network_list_5g.append(networks_dict["5g"])

        if "open_network" in self.user_params:
            pass
        else:
            networks_dict = self.get_open_network(ap_count=ap_count)
            network_list_2g.append(networks_dict["2g"])
            network_list_5g.append(networks_dict["5g"])

        if len(network_list_5g) > 1:
                self.config_5g = self._generate_legacy_ap_config(network_list_5g)
        if len(network_list_2g) > 1:
                self.config_2g = self._generate_legacy_ap_config(network_list_2g)

        for ap in range(ap_count):
            self.access_points[ap].start_ap(self.config_2g)
            self.access_points[ap].start_ap(self.config_5g)


    def _generate_legacy_ap_config(self, network_list):
        bss_settings = []
        ap_settings = network_list.pop(0)
        hostapd_config_settings = network_list.pop(0)
        for network in network_list:
            if "password" in network:
                bss_settings.append(
                    hostapd_bss_settings.BssSettings(
                        name=network["SSID"],
                        ssid=network["SSID"],
                        security=hostapd_security.Security(
                            security_mode=network["security"],
                            password=network["password"])))
            else:
                bss_settings.append(
                    hostapd_bss_settings.BssSettings(
                        name=network["SSID"], ssid=network["SSID"]))
        if "password" in hostapd_config_settings:
            config = hostapd_ap_preset.create_ap_preset(
                channel=ap_settings["channel"],
                ssid=hostapd_config_settings["SSID"],
                security=hostapd_security.Security(
                    security_mode=hostapd_config_settings["security"],
                    password=hostapd_config_settings["password"]),
                bss_settings=bss_settings,
                profile_name='whirlwind')
        else:
            config = hostapd_ap_preset.create_ap_preset(
                channel=ap_settings["channel"],
                ssid=hostapd_config_settings["SSID"],
                bss_settings=bss_settings,
                profile_name='whirlwind')

        return config
