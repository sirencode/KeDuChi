功能说明：
实现横线比例尺滑动效果，效果图如下

![image](https://github.com/sirencode/KeDuChi/tree/master/app/src/main/raw/a.png)

![image](https://github.com/sirencode/KeDuChi/tree/master/app/src/main/raw/b.png)

可动态设置
比例尺的最大，最小尺度
每个尺度代表的值的大小
刻度的进制
刻度间隔
刻度线的长短

```java
   <attr name="scale_view_max" format="integer" />
           <attr name="scale_view_min" format="integer" />
           <!-- 刻度线高度的基数，短线的1/2 长线的1/3 -->
           <attr name="scale_view_height" format="dimension" />
           <!-- 刻度线之间间隔 -->
           <attr name="scale_view_interval" format="dimension" />
           <!-- 每个几个一个长线，进制 -->
           <attr name="scale_systemscale" format="integer" />
           <!-- 每一个的值 -->
   <attr name="scle_value" format="float" />

```