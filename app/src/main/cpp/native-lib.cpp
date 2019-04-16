#include <jni.h>
#include <string>
#include <android/log.h>



//define是预处理指令 在编译过程中进行语句的替换
#define TAG "JNI_"

#define LOGD(...)   __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)


//通过JAVA+包名+类名+方法名 形成JAVA方法和C方法的映射 称为静态注册

extern "C" JNIEXPORT jstring JNICALL
Java_com_mrcodesniper_jnilive_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


jobject returnPerson(JNIEnv *env,jobject instance){
    //根据路径找到class
    jclass clazz_Native=env->FindClass("com/mrcodesniper/jnilive/Person");
    //根据class和方法名和方法签名 拿到方法ID 当前为构造方法
    jmethodID jmethodID1=env->GetMethodID(clazz_Native,"<init>","(Ljava/lang/String;)V");
    char *name="newversion";
    jstring  str=env->NewStringUTF(name);
    //拿到类 构造方法 和方法参数 拿到对象
    jobject person=env->NewObject(clazz_Native,jmethodID1,str);
    return person;
}



JNICALL
jstring backStringToJava(JNIEnv *env,jobject){
    std::string hello="hello world from c++fasfasfasf";
    return env->NewStringUTF(hello.c_str());
}

//动态注册JNI方法
jint registerMethod(JNIEnv *env){ //JNIEnv 通过指针对象可以拿到JAVA的对象
    jclass clz=env->FindClass("com/mrcodesniper/jnilive/NativeHelper");
    if(clz == NULL){
        LOGD("can not find class nativehelper");
    }
    //定义JNI方法数组 1.java方法名 2.方法对应签名 3.C++中方法名
    JNINativeMethod jniNativeMethod[]={
            {"strFromJni","()Ljava/lang/String;",(void *)backStringToJava},{"getNewPerson","()Lcom/mrcodesniper/jnilive/Person;",(void *)returnPerson}


    };
    //注册JAVA类和JNI方法
    return env->RegisterNatives(clz,jniNativeMethod, sizeof(jniNativeMethod)/ sizeof(jniNativeMethod[0]));
}

//调用动态注册方法
jint JNI_OnLoad(JavaVM *vm,void *reserved){
    JNIEnv *env =NULL;
    if(vm->GetEnv((void**)&env,JNI_VERSION_1_6)!=JNI_OK){
        return JNI_ERR;
    }
    jint result=registerMethod(env);
    LOGD("register native result:%d",result); //返回0为JNI_OK
    return JNI_VERSION_1_6;  //一定要返回版本号，否则会出错
}