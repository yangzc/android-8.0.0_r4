# Copyright (C) 2014 The Android Open Source Project
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

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := $(call all-java-files-under, src)

# tag this module as a cts test artifact
LOCAL_MODULE_TAGS := tests
LOCAL_COMPATIBILITY_SUITE := cts

LOCAL_MODULE := CtsIncidentHostTestCases

LOCAL_PROTOC_OPTIMIZE_TYPE := full
LOCAL_STATIC_JAVA_LIBRARIES := platformprotos
LOCAL_JAVA_LIBRARIES := cts-tradefed tradefed compatibility-host-util host-libprotobuf-java-full

include $(BUILD_HOST_JAVA_LIBRARY)

include $(call all-makefiles-under,$(LOCAL_PATH))
