#/usr/bin/env python3.4
#
# Copyright (C) 2016 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License. You may obtain a copy of
# the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under
# the License.
"""
Bluetooth adapter libraries
"""

from acts.test_utils.bt.BtEnum import BluetoothScanModeType
from acts.test_utils.bt.bt_test_utils import set_bt_scan_mode

import pprint


class BtaLib():
    def __init__(self, log, mac_addr, dut):
        self.advertisement_list = []
        self.dut = dut
        self.log = log
        self.mac_addr = mac_addr

    def set_scan_mode(self, scan_mode):
        """Set the Scan mode of the Bluetooth Adapter"""
        for mode in BluetoothScanModeType:
            if scan_mode == mode.name:
                set_bt_scan_mode(self.dut, mode.value)
                return

    def set_device_name(self, line):
        """Set Bluetooth Adapter Name"""
        self.dut.droid.bluetoothSetLocalName(line)

    def enable(self):
        """Enable Bluetooth Adapter"""
        self.dut.droid.bluetoothToggleState(True)

    def disable(self):
        """Disable Bluetooth Adapter"""
        self.dut.droid.bluetoothToggleState(False)

    def init_bond(self):
        """Initiate bond to PTS device"""
        self.dut.droid.bluetoothDiscoverAndBond(self.mac_addr)

    def start_discovery(self):
        """Start BR/EDR Discovery"""
        self.dut.droid.bluetoothStartDiscovery()

    def stop_discovery(self):
        """Stop BR/EDR Discovery"""
        self.dut.droid.bluetoothCancelDiscovery()

    def get_discovered_devices(self):
        """Get Discovered Br/EDR Devices"""
        if self.dut.droid.bluetoothIsDiscovering():
            self.dut.droid.bluetoothCancelDiscovery()
        self.log.info(
            pprint.pformat(self.dut.droid.bluetoothGetDiscoveredDevices()))

    def bond(self):
        """Bond to PTS device"""
        self.dut.droid.bluetoothBond(self.mac_addr)

    def disconnect(self):
        """BTA disconnect"""
        self.dut.droid.bluetoothDisconnectConnected(self.mac_addr)

    def unbond(self):
        """Unbond from PTS device"""
        self.dut.droid.bluetoothUnbond(self.mac_addr)

    def start_pairing_helper(self, line):
        """Start or stop Bluetooth Pairing Helper"""
        if line:
            self.dut.droid.bluetoothStartPairingHelper(bool(line))
        else:
            self.dut.droid.bluetoothStartPairingHelper()

    def push_pairing_pin(self, line):
        """Push pairing pin to the Android Device"""
        self.dut.droid.eventPost("BluetoothActionPairingRequestUserConfirm",
                                 line)

    def get_pairing_pin(self):
        """Get pairing PIN"""
        self.log.info(
            self.dut.ed.pop_event("BluetoothActionPairingRequest", 1))

    def fetch_uuids_with_sdp(self):
        """BTA fetch UUIDS with SDP"""
        self.log.info(self.dut.droid.bluetoothFetchUuidsWithSdp(self.mac_addr))
