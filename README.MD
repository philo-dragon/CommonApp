# CommonApp 练习各种框架

## 使用LiveData && ViewModel搭建开发框架
* UI控制层，这部分就是我们平时写的Activity和Fragment。
* ViewModel用于持有和UI元素相关的数据，以保证这些数据在屏幕旋转时不会丢失，以及负责和仓库之间进行通讯。
* 仓库层，仓库层要做的工作是自主判断接口请求的数据应该是从数据库中读取还是从网络中获取，并将数据返回给调用方。
  如果是从网络中获取的话还要将这些数据存入到数据库当中，以避免下次重复从网络中获取。简而言之，
  仓库的工作就是在本地和网络数据之间做一个分配和调度的工作，调用方不管你的数据是从何而来的，我只是要从你仓库这里获取数据而已，
  而仓库则要自主分配如何更好更快地将数据提供给调用方。

## 添加功能
* 添加ViewModel 刷新 加载 数据功能（完成 用法看DemoFragment）
* 把BaseApplicationg改成kotlin 初始化ARout和Multidex库
* 添加BaseActivity和BaseFragment文件