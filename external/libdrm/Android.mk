#
# Copyright © 2011-2012 Intel Corporation
#
# Permission is hereby granted, free of charge, to any person obtaining a
# copy of this software and associated documentation files (the "Software"),
# to deal in the Software without restriction, including without limitation
# the rights to use, copy, modify, merge, publish, distribute, sublicense,
# and/or sell copies of the Software, and to permit persons to whom the
# Software is furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice (including the next
# paragraph) shall be included in all copies or substantial portions of the
# Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL
# THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
# FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
# IN THE SOFTWARE.
#

ifneq ($(TARGET_USE_PRIVATE_LIBDRM),true)
LOCAL_PATH := $(call my-dir)

# Import variables LIBDRM_{,H_,INCLUDE_H_,INCLUDE_VMWGFX_H_}FILES
include $(LOCAL_PATH)/Makefile.sources

common_CFLAGS := \
	-DHAVE_VISIBILITY=1 \
	-DHAVE_LIBDRM_ATOMIC_PRIMITIVES=1 \
	-Wno-enum-conversion \
	-Wno-missing-field-initializers \
	-Wno-pointer-arith \
	-Wno-sign-compare \
	-Wno-tautological-compare \
	-Wno-unused-parameter

# Static library for the device (recovery)
include $(CLEAR_VARS)

LOCAL_MODULE := libdrm

LOCAL_SRC_FILES := $(filter-out %.h,$(LIBDRM_FILES))
LOCAL_EXPORT_C_INCLUDE_DIRS := \
	$(LOCAL_PATH) \
	$(LOCAL_PATH)/include/drm

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/include/drm

LOCAL_CFLAGS := \
	$(common_CFLAGS)

include $(BUILD_STATIC_LIBRARY)

# Dynamic library for the device
include $(CLEAR_VARS)

LOCAL_MODULE := libdrm

LOCAL_SRC_FILES := $(filter-out %.h,$(LIBDRM_FILES))
LOCAL_EXPORT_C_INCLUDE_DIRS := \
	$(LOCAL_PATH) \
	$(LOCAL_PATH)/include/drm

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/include/drm

LOCAL_CFLAGS := \
	$(common_CFLAGS)

include $(BUILD_SHARED_LIBRARY)

include $(call all-makefiles-under,$(LOCAL_PATH))
endif
