# Copyright (C) 2015 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#

#disble build in PDK, should add prebuilts/fullsdk to make this work
ifneq ($(TARGET_BUILD_PDK),true)

LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_CERTIFICATE := platform
LOCAL_MODULE_TAGS := optional
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res
LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := CarUiProvider
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_DEX_PREOPT := nostripping

include packages/services/Car/car-support-lib/car-support.mk

include $(BUILD_PACKAGE)

endif #TARGET_BUILD_PDK
