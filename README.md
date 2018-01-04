# Huluwa_VS_Monster——葫芦娃大战妖精的故事
## 总述
#### 介绍
- 葫芦娃大战妖精是一款Java语言编写的小游戏。
- 玩家只需进入游戏界面，用上帝之手按下Space键，即可感受葫芦娃与妖精的殊死搏斗。
- 不满意可重新来过，满意可回顾历史。
#### 故事背景
- 传说葫芦山里关着蝎子精和蛇，穿山甲不小心打穿了山洞，于是两个妖精逃了出来，从此百姓遭难。只有种出七色葫芦，才能消灭这两个妖精。
一段时间过去了，老爷爷种的葫芦娃终于长大了，他们与妖精展开了最后的较量...
#### 游戏界面
![image](https://github.com/triumphalLiu/Huluwa_VS_Monster/blob/master/screenshot/MainScreenShot.png "应用截图")
***
## 主界面
- 作为上帝，你可以控制时间，让时光倒流，让时光重现...直到你满意为止。
- 上帝之手可以做的：

|选项|功能描述|
|:------|:----|
|开始游戏|当作什么都没有发生过，开始一场葫芦娃与妖精新的较量|
|读取历史|谁说过去只是过去，打开存档，再回味一次精彩瞬间|
|关于游戏|调用默认浏览器跳转到Github仓库页面|
|退出游戏|关闭程序窗口，退出游戏|
***
## 游戏界面
- 葫芦娃和老爷爷一同对战妖精，在空间的左侧以长蛇阵站队
- 小喽啰，蛇精和蝎子精迎战葫芦娃，在空间的右侧以鹤翼阵站队
- 上帝按下空格后，葫芦娃们和妖精们将开始搏斗，知道有一方全部阵亡
- 作为上帝，你可以随时按下Esc终止战争
- 数据说明:葫芦娃胜率:60%，妖精胜率:50%，爷爷胜率:40%
#### 游戏截图
![image](https://github.com/triumphalLiu/Huluwa_VS_Monster/blob/master/screenshot/FieldScreenShot.png "应用截图")
***
## 结果界面
- 当妖精全部阵亡后，葫芦娃和老爷爷获胜，从此过上了幸福的生活。
- 葫芦娃作为最后的胜利者，会摆出造型：
![image](https://github.com/triumphalLiu/Huluwa_VS_Monster/blob/master/screenshot/HuluwaWinsScreenShot.png "应用截图")
- 当葫芦娃和爷爷全部阵亡后，妖精获胜，葫芦娃最终还是没能抵挡妖精的侵袭，人间再次沦为炼狱...
- 妖精作为最后的胜利者，会摆出造型：
![image](https://github.com/triumphalLiu/Huluwa_VS_Monster/blob/master/screenshot/MonsterWinsScreenShot.png "应用截图")
***
## 操作说明
<table><tr>
<td> <strong>界面</strong></td> 
<td> <strong>快捷键</strong></td>
<td> <strong>操作描述</strong></td></tr>
<tr><td rowspan="3">主界面</td>    
<td>S</td>
<td>开始游戏</td></tr>
<tr><td>L</td>  
<td>读取历史</td></tr>
<tr><td>Esc / Q</td>  
<td>退出游戏</td></tr>
<tr><td rowspan="2">游戏界面</td>    
<td>Space</td>
<td>开始战斗</td></tr>
<tr><td>Esc</td>  
<td>返回主界面</td></tr>
<tr><td rowspan="3">结果界面</td>    
<td>L</td>  
<td>读取历史</td></tr>
<tr><td>S</td>  
<td>保存记录(仅新的游戏结束后有效)</td></tr>
<tr><td>Esc</td>
<td>返回主界面</td></tr>
</table>

***
## 代码框架
### GUI/图形界面
采用JavaFX作为界面框架，使用Scene Builder搭建界面，通过fxml文件作为介质载入。
### Package/包
#### creature 包含了各种生物的实现，包括：
* Creature，作为interface，规定了生物具有的功能。
* Huluwa，葫芦娃
* Grandpa，老爷爷
* Monster， 妖精
* Xiezijing， 蝎子精（继承自妖精）
* Xiaolouluo，小喽啰（继承自妖精）
* Snake，蛇精（继承自妖精）
* Space，空白（特殊的生物，如空气一般虚无缥缈）
#### field 包含了二维战斗场景的实现，包括：
* Field，二维空间，SizeX*SizeY的空间大小，提供了一系列对外的接口来改变Field战场的状态。
* Position，单个位置，可以放置唯一的生物。
#### frame 包含了UI相关的实现，包括：
* Animation，动画效果，通过定时器实现了淡入淡出效果，界面背景图片变换时会展现。
* FileModel，文件模块，用于保存字符串到文件，以及从文件读取字符串。
* Controller，JavaFX界面的事件处理类，将UI层和事件层分离。
* Main，程序入口，fxml文件的读取，主框架的搭建。
#### stratagem 包含了一些阵法的实现，包括：
* Stratagem，作为interface，规定了阵法应具备的方法。
* HeyiStratagem，鹤翼阵
* YanxingStratagem，雁形阵
* ChangsheStratagem，长蛇阵
### Test/测试
主要对field.Field中的方法进行测试，包括：
* Add方法是否能正确在指定位置放置生物。
* Delete方法是否能正确移除指定位置的生物。
* IsRowHaveEnemy方法是否能正确判断该行是否存在敌人。
* IsGameOver方法是否能正确判断游戏是否结束。
***
## 结束语
- Java游戏的开发过程还是很有趣的，虽然遇到了很多的问题，但是查找资料并尝试解决问题之后的成就感很让人满足。
- 上这门课学到了很多知识，比如以前没有尝试过的并发编程，虽然考试的时候不太理想，但是能写出这个游戏（故事）很让人满足。
- 改掉了拖延症，写作业效率变高了。
- **写应用同时也要会Photoshop啊...抠图拼图什么的**
- 本项目中所有资源均来自互联网，在此向资源提供者致谢，如有侵权请联系作者<liu@triumphal.cn>删除。
- 感谢曹老师余老师的授课，merci.
- 如对此项目有意见或建议欢迎交流:<liu@triumphal.cn>
***
