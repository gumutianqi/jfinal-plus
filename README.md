# JFinal-Plus 开箱即用扩展工具

#####v1.0.0 发布
1. 基于JFinal 2.2 版本;
2. 集成[JFinal-Ext](http://git.oschina.net/zhouleib1412/jfinal-ext)和[JFinal-Ext2](http://git.oschina.net/brucezcq/JFinal-ext2)最新版本的稳定工具(感谢);
3. 集成Beetl2支持,整合Beetl+Shiro使用;
4. 集成Beetl2 + XSS 放注入插件,基于`antisamy`;
5. 重写Redis插件,可扩展和添加方法(在JFinal2.2的Redis不支持扩展前的过度使用);
6. Log插件集成Slf4j
7. 更好用的MongoDB插件,完美与Model Record整合,轻松互转;
8. 集成[MongoDB-Plus](https://github.com/T-baby/MongoDB-Plugin)工具包,推荐使用;
9. 集成Quartz插件,封装完善,傻瓜化调用Kit调用模式,入口为`QuartzKit`
10. 集成为Spring提取的`AntPathMatcher`工具,轻松在interceptor和handler中进行Path过滤和包含使用;
11. 提供继承的BaseController, 扩展Controller的功能,更加方便使用;
12. 重磅集成了17个常用的工具(见包`com.plus.kit`),都是日常使用最多的工具;
13. 主要依赖集成[lombok工具](https://projectlombok.org/),减少不必要的代码,需要IDE安装插件;
14. 集成了一些列优秀的开源工具,见pom文件;
