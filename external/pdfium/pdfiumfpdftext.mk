LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE:= libpdfiumfpdftext

LOCAL_ARM_MODE := arm
LOCAL_NDK_STL_VARIANT := gnustl_static

LOCAL_CFLAGS += -O3 -fstrict-aliasing -fprefetch-loop-arrays -fexceptions
LOCAL_CFLAGS += -Wno-non-virtual-dtor -Wall -DOPJ_STATIC \
                -DV8_DEPRECATION_WARNINGS -D_CRT_SECURE_NO_WARNINGS

# Mask some warnings. These are benign, but we probably want to fix them
# upstream at some point.
LOCAL_CFLAGS += -Wno-sign-compare -Wno-unused-parameter
LOCAL_CLANG_CFLAGS += -Wno-sign-compare

LOCAL_SRC_FILES := \
    core/fpdftext/cpdf_linkextract.cpp \
    core/fpdftext/cpdf_textpage.cpp \
    core/fpdftext/cpdf_textpagefind.cpp \
    core/fpdftext/unicodenormalizationdata.cpp \

LOCAL_C_INCLUDES := \
    external/pdfium \
    external/freetype/include \
    external/freetype/include/freetype

include $(BUILD_STATIC_LIBRARY)
