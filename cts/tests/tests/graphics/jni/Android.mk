# Copyright 2016 The Android Open Source Project
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

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libctsgraphics_jni

LOCAL_MODULE_TAGS := tests

LOCAL_SRC_FILES := \
	CtsGraphicsJniOnLoad.cpp \
	android_graphics_cts_ANativeWindowTest.cpp \
	android_graphics_cts_BitmapTest.cpp \
	android_graphics_cts_VulkanFeaturesTest.cpp

LOCAL_CFLAGS += -Wall -Werror

LOCAL_STATIC_LIBRARIES := libvkjson_ndk
LOCAL_SHARED_LIBRARIES := libandroid libvulkan libnativewindow liblog libdl libjnigraphics
LOCAL_NDK_STL_VARIANT := c++_static

LOCAL_SDK_VERSION := current

include $(BUILD_SHARED_LIBRARY)
