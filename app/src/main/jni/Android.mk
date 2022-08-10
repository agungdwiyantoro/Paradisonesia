LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := credentials
LOCAL_SRC_FILES := credentials.c

include $(BUILD_SHARED_LIBRARY)