

#cmake最小支持版本
cmake_minimum_required(VERSION 3.4.1)




add_library( # 生成so库的名称后缀  生成的为libnative-lib.so
        native-lib

        # SO库类型 SHARED 动态库 在运行中加载  STATIC静态库 在被链接的时候使用
        SHARED

        # 设置源文件位置 有多个   IMPORTED 源文件是导入的
        native-lib.cpp)


add_library(lib2 SHARED IMPORTED)


# 关联so库 为lib2
set_target_properties(lib2
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libdata.so)


# 从系统寻找库
find_library( # 查找系统中的日志库
        log-lib

        #指定找到库的名称为log
        log)


## 配置相互依赖关系
target_link_libraries(
        #目标库
        native-lib

        #引用其他库 系统库必须加${} 第三方直接写名字 需要换行
        ${log-lib})