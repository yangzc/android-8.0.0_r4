LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE:= libpdfiumfpdfapi

LOCAL_ARM_MODE := arm
LOCAL_NDK_STL_VARIANT := gnustl_static

LOCAL_CFLAGS += -O3 -fstrict-aliasing -fprefetch-loop-arrays -fexceptions
LOCAL_CFLAGS += -Wno-non-virtual-dtor -Wall -DOPJ_STATIC \
                -DV8_DEPRECATION_WARNINGS -D_CRT_SECURE_NO_WARNINGS

# Mask some warnings. These are benign, but we probably want to fix them
# upstream at some point.
LOCAL_CFLAGS += -Wno-sign-compare -Wno-unused-parameter -Wno-missing-field-initializers
LOCAL_CLANG_CFLAGS += -Wno-sign-compare

LOCAL_SRC_FILES := \
    core/fpdfapi/cmaps/CNS1/Adobe-CNS1-UCS2_5.cpp \
    core/fpdfapi/cmaps/CNS1/B5pc-H_0.cpp \
    core/fpdfapi/cmaps/CNS1/B5pc-V_0.cpp \
    core/fpdfapi/cmaps/CNS1/CNS-EUC-H_0.cpp \
    core/fpdfapi/cmaps/CNS1/CNS-EUC-V_0.cpp \
    core/fpdfapi/cmaps/CNS1/ETen-B5-H_0.cpp \
    core/fpdfapi/cmaps/CNS1/ETen-B5-V_0.cpp \
    core/fpdfapi/cmaps/CNS1/ETenms-B5-H_0.cpp \
    core/fpdfapi/cmaps/CNS1/ETenms-B5-V_0.cpp \
    core/fpdfapi/cmaps/CNS1/HKscs-B5-H_5.cpp \
    core/fpdfapi/cmaps/CNS1/HKscs-B5-V_5.cpp \
    core/fpdfapi/cmaps/CNS1/UniCNS-UCS2-H_3.cpp \
    core/fpdfapi/cmaps/CNS1/UniCNS-UCS2-V_3.cpp \
    core/fpdfapi/cmaps/CNS1/UniCNS-UTF16-H_0.cpp \
    core/fpdfapi/cmaps/CNS1/cmaps_cns1.cpp \
    core/fpdfapi/cmaps/GB1/Adobe-GB1-UCS2_5.cpp \
    core/fpdfapi/cmaps/GB1/GB-EUC-H_0.cpp \
    core/fpdfapi/cmaps/GB1/GB-EUC-V_0.cpp \
    core/fpdfapi/cmaps/GB1/GBK-EUC-H_2.cpp \
    core/fpdfapi/cmaps/GB1/GBK-EUC-V_2.cpp \
    core/fpdfapi/cmaps/GB1/GBK2K-H_5.cpp \
    core/fpdfapi/cmaps/GB1/GBK2K-V_5.cpp \
    core/fpdfapi/cmaps/GB1/GBKp-EUC-H_2.cpp \
    core/fpdfapi/cmaps/GB1/GBKp-EUC-V_2.cpp \
    core/fpdfapi/cmaps/GB1/GBpc-EUC-H_0.cpp \
    core/fpdfapi/cmaps/GB1/GBpc-EUC-V_0.cpp \
    core/fpdfapi/cmaps/GB1/UniGB-UCS2-H_4.cpp \
    core/fpdfapi/cmaps/GB1/UniGB-UCS2-V_4.cpp \
    core/fpdfapi/cmaps/GB1/cmaps_gb1.cpp \
    core/fpdfapi/cmaps/Japan1/83pv-RKSJ-H_1.cpp \
    core/fpdfapi/cmaps/Japan1/90ms-RKSJ-H_2.cpp \
    core/fpdfapi/cmaps/Japan1/90ms-RKSJ-V_2.cpp \
    core/fpdfapi/cmaps/Japan1/90msp-RKSJ-H_2.cpp \
    core/fpdfapi/cmaps/Japan1/90msp-RKSJ-V_2.cpp \
    core/fpdfapi/cmaps/Japan1/90pv-RKSJ-H_1.cpp \
    core/fpdfapi/cmaps/Japan1/Add-RKSJ-H_1.cpp \
    core/fpdfapi/cmaps/Japan1/Add-RKSJ-V_1.cpp \
    core/fpdfapi/cmaps/Japan1/Adobe-Japan1-UCS2_4.cpp \
    core/fpdfapi/cmaps/Japan1/EUC-H_1.cpp \
    core/fpdfapi/cmaps/Japan1/EUC-V_1.cpp \
    core/fpdfapi/cmaps/Japan1/Ext-RKSJ-H_2.cpp \
    core/fpdfapi/cmaps/Japan1/Ext-RKSJ-V_2.cpp \
    core/fpdfapi/cmaps/Japan1/H_1.cpp \
    core/fpdfapi/cmaps/Japan1/UniJIS-UCS2-HW-H_4.cpp \
    core/fpdfapi/cmaps/Japan1/UniJIS-UCS2-HW-V_4.cpp \
    core/fpdfapi/cmaps/Japan1/UniJIS-UCS2-H_4.cpp \
    core/fpdfapi/cmaps/Japan1/UniJIS-UCS2-V_4.cpp \
    core/fpdfapi/cmaps/Japan1/V_1.cpp \
    core/fpdfapi/cmaps/Japan1/cmaps_japan1.cpp \
    core/fpdfapi/cmaps/Korea1/Adobe-Korea1-UCS2_2.cpp \
    core/fpdfapi/cmaps/Korea1/KSC-EUC-H_0.cpp \
    core/fpdfapi/cmaps/Korea1/KSC-EUC-V_0.cpp \
    core/fpdfapi/cmaps/Korea1/KSCms-UHC-HW-H_1.cpp \
    core/fpdfapi/cmaps/Korea1/KSCms-UHC-HW-V_1.cpp \
    core/fpdfapi/cmaps/Korea1/KSCms-UHC-H_1.cpp \
    core/fpdfapi/cmaps/Korea1/KSCms-UHC-V_1.cpp \
    core/fpdfapi/cmaps/Korea1/KSCpc-EUC-H_0.cpp \
    core/fpdfapi/cmaps/Korea1/UniKS-UCS2-H_1.cpp \
    core/fpdfapi/cmaps/Korea1/UniKS-UCS2-V_1.cpp \
    core/fpdfapi/cmaps/Korea1/UniKS-UTF16-H_0.cpp \
    core/fpdfapi/cmaps/Korea1/cmaps_korea1.cpp \
    core/fpdfapi/cmaps/fpdf_cmaps.cpp \
    core/fpdfapi/cpdf_modulemgr.cpp \
    core/fpdfapi/cpdf_pagerendercontext.cpp \
    core/fpdfapi/edit/cpdf_pagecontentgenerator.cpp \
    core/fpdfapi/edit/fpdf_edit_create.cpp \
    core/fpdfapi/font/cpdf_cidfont.cpp \
    core/fpdfapi/font/cpdf_font.cpp \
    core/fpdfapi/font/cpdf_fontencoding.cpp \
    core/fpdfapi/font/cpdf_simplefont.cpp \
    core/fpdfapi/font/cpdf_truetypefont.cpp \
    core/fpdfapi/font/cpdf_type1font.cpp \
    core/fpdfapi/font/cpdf_type3char.cpp \
    core/fpdfapi/font/cpdf_type3font.cpp \
    core/fpdfapi/font/fpdf_font.cpp \
    core/fpdfapi/font/fpdf_font_cid.cpp \
    core/fpdfapi/font/ttgsubtable.cpp \
    core/fpdfapi/page/cpdf_allstates.cpp \
    core/fpdfapi/page/cpdf_clippath.cpp \
    core/fpdfapi/page/cpdf_color.cpp \
    core/fpdfapi/page/cpdf_colorspace.cpp \
    core/fpdfapi/page/cpdf_colorstate.cpp \
    core/fpdfapi/page/cpdf_contentmark.cpp \
    core/fpdfapi/page/cpdf_contentmarkitem.cpp \
    core/fpdfapi/page/cpdf_contentparser.cpp \
    core/fpdfapi/page/cpdf_docpagedata.cpp \
    core/fpdfapi/page/cpdf_form.cpp \
    core/fpdfapi/page/cpdf_formobject.cpp \
    core/fpdfapi/page/cpdf_generalstate.cpp \
    core/fpdfapi/page/cpdf_graphicstates.cpp \
    core/fpdfapi/page/cpdf_image.cpp \
    core/fpdfapi/page/cpdf_imageobject.cpp \
    core/fpdfapi/page/cpdf_meshstream.cpp \
    core/fpdfapi/page/cpdf_page.cpp \
    core/fpdfapi/page/cpdf_pagemodule.cpp \
    core/fpdfapi/page/cpdf_pageobject.cpp \
    core/fpdfapi/page/cpdf_pageobjectholder.cpp \
    core/fpdfapi/page/cpdf_pageobjectlist.cpp \
    core/fpdfapi/page/cpdf_path.cpp \
    core/fpdfapi/page/cpdf_pathobject.cpp \
    core/fpdfapi/page/cpdf_pattern.cpp \
    core/fpdfapi/page/cpdf_shadingobject.cpp \
    core/fpdfapi/page/cpdf_shadingpattern.cpp \
    core/fpdfapi/page/cpdf_streamcontentparser.cpp \
    core/fpdfapi/page/cpdf_streamparser.cpp \
    core/fpdfapi/page/cpdf_textobject.cpp \
    core/fpdfapi/page/cpdf_textstate.cpp \
    core/fpdfapi/page/cpdf_tilingpattern.cpp \
    core/fpdfapi/page/fpdf_page_colors.cpp \
    core/fpdfapi/page/fpdf_page_func.cpp \
    core/fpdfapi/parser/cfdf_document.cpp \
    core/fpdfapi/parser/cpdf_array.cpp \
    core/fpdfapi/parser/cpdf_boolean.cpp \
    core/fpdfapi/parser/cpdf_crypto_handler.cpp \
    core/fpdfapi/parser/cpdf_data_avail.cpp \
    core/fpdfapi/parser/cpdf_dictionary.cpp \
    core/fpdfapi/parser/cpdf_document.cpp \
    core/fpdfapi/parser/cpdf_hint_tables.cpp \
    core/fpdfapi/parser/cpdf_indirect_object_holder.cpp \
    core/fpdfapi/parser/cpdf_linearized_header.cpp \
    core/fpdfapi/parser/cpdf_name.cpp \
    core/fpdfapi/parser/cpdf_null.cpp \
    core/fpdfapi/parser/cpdf_number.cpp \
    core/fpdfapi/parser/cpdf_object.cpp \
    core/fpdfapi/parser/cpdf_parser.cpp \
    core/fpdfapi/parser/cpdf_reference.cpp \
    core/fpdfapi/parser/cpdf_security_handler.cpp \
    core/fpdfapi/parser/cpdf_simple_parser.cpp \
    core/fpdfapi/parser/cpdf_stream.cpp \
    core/fpdfapi/parser/cpdf_stream_acc.cpp \
    core/fpdfapi/parser/cpdf_string.cpp \
    core/fpdfapi/parser/cpdf_syntax_parser.cpp \
    core/fpdfapi/parser/fpdf_parser_decode.cpp \
    core/fpdfapi/parser/fpdf_parser_utility.cpp \
    core/fpdfapi/render/cpdf_charposlist.cpp \
    core/fpdfapi/render/cpdf_devicebuffer.cpp \
    core/fpdfapi/render/cpdf_dibsource.cpp \
    core/fpdfapi/render/cpdf_dibtransferfunc.cpp \
    core/fpdfapi/render/cpdf_docrenderdata.cpp \
    core/fpdfapi/render/cpdf_imagecacheentry.cpp \
    core/fpdfapi/render/cpdf_imageloader.cpp \
    core/fpdfapi/render/cpdf_imagerenderer.cpp \
    core/fpdfapi/render/cpdf_pagerendercache.cpp \
    core/fpdfapi/render/cpdf_progressiverenderer.cpp \
    core/fpdfapi/render/cpdf_rendercontext.cpp \
    core/fpdfapi/render/cpdf_renderoptions.cpp \
    core/fpdfapi/render/cpdf_renderstatus.cpp \
    core/fpdfapi/render/cpdf_scaledrenderbuffer.cpp \
    core/fpdfapi/render/cpdf_textrenderer.cpp \
    core/fpdfapi/render/cpdf_transferfunc.cpp \
    core/fpdfapi/render/cpdf_type3cache.cpp \
    core/fpdfapi/render/cpdf_type3glyphs.cpp \

LOCAL_C_INCLUDES := \
    external/pdfium \
    external/freetype/include \
    external/freetype/include/freetype

include $(BUILD_STATIC_LIBRARY)
