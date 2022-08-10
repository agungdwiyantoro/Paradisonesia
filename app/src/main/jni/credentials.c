#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_devfutech_paradisonesia_external_config_BaseConfig_getBaseUrlStagingCustomer(JNIEnv *env,jobject thiz) {
    return (*env)->NewStringUTF(env, "https://paradisonesia-api-customer.coretanstudio.com/api/v1/");
}

JNIEXPORT jstring JNICALL
Java_com_devfutech_paradisonesia_external_config_BaseConfig_getMidtransClientKeyStagingCustomer(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "SB-Mid-client-cx6e3motKWjN8gjD");
}