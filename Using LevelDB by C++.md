# 1.  brew安装levelDb

```shell
brew install leveldb
```



# 2. 源码安装levelDb

1. clone leveldb仓库：

   ```shell
   https://github.com/google/leveldb.git
   ```

2. 安装cmake

3. 编译安装leveldb

   ```shell
   mkdir -p build && cd build
   cmake -DCMAKE_BUILD_TYPE=Release .. && cmake --build .
   make install
   ```

   

## 3. 测试是否安装成功

1. 新建c++测试文件main.cc

```C++
#include <assert.h>
#include <string.h>
#include <leveldb/db.h>
#include <iostream>


int main(int argc, char** argv)
{
    leveldb::DB* db;
    leveldb::Options options;
    // 如果打开已存在数据库的时候，需要抛出错误，将以下代码插在leveldb::DB::Open方法前面
    options.create_if_missing = true;
    // 打开一个数据库实例
    leveldb::Status status = leveldb::DB::Open(options, "/tmp/testdb", &db);
    assert(status.ok());
    // LevelDB提供了Put、Get和Delete三个方法对数据库进行添加、查询和删除
    std::string key = "key";
    std::string value = "value";
    // 添加key=value
    status = db->Put(leveldb::WriteOptions(), key, value);
    assert(status.ok());
    // 根据key查询value
    status = db->Get(leveldb::ReadOptions(), key, &value);
    assert(status.ok());
    std::cout<<value<<std::endl;
    // 修改操作（原生没有提供）由添加和删除合起来实现
    std::string key2 = "key2";
    // 添加key2=value
    status = db->Put(leveldb::WriteOptions(),key2,value);
    assert(status.ok());
    // 删除key
    status = db->Delete(leveldb::WriteOptions(), key);
    // 查询key2
    assert(status.ok());
    status = db->Get(leveldb::ReadOptions(), key2, &value);
    assert(status.ok());
    std::cout<<key2<<"=="<<value<<std::endl;
    // 查询key
    status = db->Get(leveldb::ReadOptions(), key, &value);
    if (!status.ok()) {
        std::cerr << key << ": " << status.ToString() << std::endl;
    } else {
        std::cout << key << "==" << value << std::endl;
    }
    delete db;
    return 0;
}
```

编译执行：

```shell
g++ main.cc -o main -lpthread -lleveldb -std=c++11
```

