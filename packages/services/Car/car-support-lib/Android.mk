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

#Build prebuilt android.support.car library
include $(CLEAR_VARS)

LOCAL_AAPT_FLAGS := --auto-add-overlay
LOCAL_MODULE := android.support.car-prebuilt
LOCAL_MODULE_CLASS := JAVA_LIBRARIES

LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res

LOCAL_MANIFEST_FILE := AndroidManifest.xml

LOCAL_SRC_FILES := $(call all-java-files-under, src) $(call all-Iaidl-files-under, src)
LOCAL_JAVA_LIBRARIES += android.car\
						android-support-v4 \
                        android-support-v7-appcompat \
                        android-support-v7-recyclerview \
                        android-support-v7-cardview \
                        android-support-annotations
# Specify 1.7 for backwards compatibility.
# Otherwise the lib won't be usable on pre-N devices
LOCAL_JAVA_LANGUAGE_VERSION := 1.7

LOCAL_PROGUARD_ENABLED := custom optimization obfuscation
LOCAL_PROGUARD_FLAGS := -dontwarn
LOCAL_PROGUARD_FLAG_FILES := proguard-release.flags proguard-extra-keeps.flags

include $(BUILD_STATIC_JAVA_LIBRARY)

ifeq ($(BOARD_IS_AUTOMOTIVE), true)
 $(call dist-for-goals,dist_files,$(built_aar):android.support.car.aar)
endif

# Build the resources.
include $(CLEAR_VARS)
LOCAL_MODULE := android.support.car-res
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res
LOCAL_RESOURCE_DIR += frameworks/support/v7/appcompat/res
LOCAL_RESOURCE_DIR += frameworks/support/v7/recyclerview/res
LOCAL_RESOURCE_DIR += frameworks/support/v7/cardview/res

LOCAL_AAPT_FLAGS := --auto-add-overlay \
    --extra-packages android.support.v7.appcompat \
    --extra-packages android.support.v7.recyclerview \
    --extra-packages android.support.v7.cardview

LOCAL_JAR_EXCLUDE_FILES := none
LOCAL_MANIFEST_FILE := AndroidManifest.xml

LOCAL_JAVA_LANGUAGE_VERSION := 1.7
include $(BUILD_STATIC_JAVA_LIBRARY)

# Build support library.
# ---------------------------------------------
include $(CLEAR_VARS)

LOCAL_MODULE := android.support.car

LOCAL_SRC_FILES := $(call all-java-files-under, src) $(call all-Iaidl-files-under, src)

LOCAL_STATIC_JAVA_LIBRARIES += android-support-v4 \
                               android-support-v7-appcompat \
                               android-support-v7-recyclerview \
                               android-support-v7-cardview \
                               android-support-annotations

LOCAL_JAVA_LIBRARIES += android.car \
                        android.support.car-res

LOCAL_JAVA_LANGUAGE_VERSION := 1.7
include $(BUILD_STATIC_JAVA_LIBRARY)

# API Check
# ---------------------------------------------
car_module := $(LOCAL_MODULE)
car_module_src_files := $(LOCAL_SRC_FILES)
car_module_proguard_file := $(LOCAL_PATH)/proguard-release.flags
car_module_api_dir := $(LOCAL_PATH)/api
car_module_java_libraries := $(LOCAL_JAVA_LIBRARIES) $(LOCAL_STATIC_JAVA_LIBRARIES) framework
car_module_java_packages := android.support.car*
car_module_include_proguard := true
include $(CAR_API_CHECK)


# Generate offline docs
#---------------------------------------------
include $(CLEAR_VARS)

LOCAL_SRC_FILES := $(call all-java-files-under, src) $(call all-Iaidl-files-under, src)

LOCAL_DROIDDOC_SOURCE_PATH := $(LOCAL_PATH)/src

LOCAL_JAVA_LIBRARIES := \
    android.car \
    android.support.car-res \
    android-support-v4 \
    android-support-v7-appcompat \
    android-support-v7-recyclerview \
    android-support-v7-cardview \
    android-support-annotations

LOCAL_MODULE := android.support.car
LOCAL_MODULE_TAGS := optional

LOCAL_MODULE_CLASS := JAVA_LIBRARIES
LOCAL_IS_HOST_MODULE := false

LOCAL_ADDITIONAL_JAVA_DIR := $(call intermediates-dir-for,$(LOCAL_MODULE_CLASS),android.support.car,,COMMON)

LOCAL_DROIDDOC_CUSTOM_TEMPLATE_DIR := external/doclava/res/assets/templates-sdk

include $(BUILD_DROIDDOC)


endif #TARGET_BUILD_PDK
