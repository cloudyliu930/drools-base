设计模式：
大量的内部类方式实现单例，少量Double Check实现

名词：
MVEL：表达式语法
DMN：提供一个模型决策结构，从而使组织的策略可以用图形清晰的地描绘出来，通过业务分析准确的定义，使其自动化（可选地）
DSL：领域特定语言
kie-maven-plugin：编译检测
Phreak:允许某种程度的并行化
OOPATH:
Predictive Modeling Markup Language（PMML）：回归  记分卡  树
OptaPlanner：轻量化规划调度引擎
Fact：类似Java Bean，规则引擎中修改Fact对象后如果需要其他规则获取最新Fact值需要调用update方法

类：
KieService：该接口提供了很多方法，可以通过这些方法访问KIE 关于构建和运行的相关对象，
			* 比如说可以获取KieContainer，利用KieContainer 来访问KBase 和KSession 等信息；
			* 可以获取KieRepository 对象，利用KieRepository 来管理KieModule 等。
			* KieServices : 就是一个中心，通过它来获取的各种对象来完成规则构建、管理和执行等操作。
KieBase(RuleBase)：应用程序知识定义的存储库，它将包含规则，流程，函数和类型模型。
KieModule：KieBase集合
KieSession：与Drools引擎打交道，对运行时数据进行规则运算
KieRepository：充当所有KieModule的存储库
KieContainer：创建KBase、KSession
KieProject：KieContainer 通过KieProject 来初始化、构造KieModule，并将KieModule 存放到KieRepository 中，然后KieContainer 可以通过KieProject 来查找KieModule 定义的信息，
并根据这些信息构造KieBase 和KieSession。
ClasspathKieProject：ClasspathKieProject 实现了KieProject 接口，它提供了根据类路径中的kmodule.xml配置文件构造KieModule 的能力，也就是我们能够基于Maven 构造Drools 组件的基本保障
之一。
KieScanner：检测项目版本是否已安装

KieFileSystem:虚拟文件系统
KieModuleModel：
KieBuilder:
KieResources:


属性：
global：全局变量不会插入到Working Memory中，多个rule可以在本rule中修改值，但是其他rule不会被发现
变量不可修改，引用类型数据可以被修改
activation-group：该属性的作用是将若干个规则划分成一个组，用一个字符串来给这个组命名，这样在执
行的时候，具有相同 activation-group 属性的规则中只要有一个会被执行，其它的规则。
都将不再执行
agenda-group：设置规则组，必须显式setFocus才能触发，或者设置auto-focus true，一般与auto-focus搭配使用
auto-focus：在已设置了 agenda-group 的规则
上设置该规则是否可以自动读取 Focus，如果该属性设置为true，那么在引擎执行时，就
56 / 341
不需要显示的为某个Agenda-Group 设置 Focus，否则需要
date-effective：日期比较小于等于当前时间才会执行规则
date-expires：设置有效期，日期比较大于当前时间才会执行规则
dialect：方言  MVEL Java
duration：定时器（已被淘汰）
enabled：规则是否可用
lock-on-active：规则上锁，fact对象的修改不会影响当前规则的条件判断
	注意：如果前置规则修改fact对象，当前规则判断fact中某个属性时可能会导致永远不匹配（working memory中值已修改，当前规则中匹配的是之前值，两者冲突时永远不会匹配成功）

no-loop：防止死循环（当调用update方法的时候，会将fact更新到Working Memory中，这是引擎会再次检查所有规则）
ruleflow-group：规则流
salience：优先级，数字越大优先级越高。

方法：
insert：
update：
retract/delete：
halt: 执行完当前规则后，不再执行其他未执行规则
fireAllRules：
第一种：int fireAllRules() 不多说了，执行所有满足条件的规则
第二种：int fireAllRules(int max) 执行规则的最大数量，简单的说，执行多少条规则
第三种：int fireAllRules(AgendaFilter agendaFilter) 指定规则名的方式
第一种：int fireAllRules(AgendaFilter agendaFilter, int max);指定规则名，并执行
多少条

语法：
query：从工作内存中查询Fact对象
Function：不能直接写在when中，可以写成eval(function); 函数定义如下：
	function String xxx()   /  java方法  import function xxxxxx
类的声明及元数据的用法：
1、声明新类型：Drools 开箱即可操作纯java 对象作为事实。然而，用户有时候可能
希望直接定义规则引擎的原型（model），不用担心象在 java 这样的低级语言中创建
原型（models）。在其他时候，已经构建了一个域原型，但是终端用户希望或需要使用
另外的实体，主要是用于推理过程期间的，而补充这个原型。
2、 声明元数据：事实可能有与之相关联的元信息。元信息的例子包括任何类型的数据，
它们并没有被事实的属性表现出来，而且在该事实类型的所有实例中是一致的。这些元
信息在运行时可以被引擎查询，并可以用于推理过程。
declare：定义类，可以在java中通过FactType获取
元数据：
1）、@key 属性将被用作一个关键标识符的类型,所以,生成的类将实现equals()和hashCode()方法时
考虑属性比较这种类型的实例。
timer：两种定时器模式(timer、cron)，需要通过fireUntilHalt触发
forall：
from：在使用 form 时，你必须小心，特别是与 lock-on-active 规则属性联合使用时，因
为它可能产生不希望的结果。
accumulate：
	average 平均值
	min 最小值
	max 最大值
	count 统计
	sum 求和
	collectList 返回List
	collectSet 返回HastSet
extends：规则继承（抽取公共代码）
do：   do[then1]   then[then1] 建议配套使用


session:
stateful:有状态
StatelessKieSession：无状态 对stateful进行了包装，调用execute时内部实现创建一个StatefulKnowledgeSessionImpl，然后调用fireAllRules、dispose方法

WorkBench：
workbench-models-datamodel-api：提供条件/动作定制
RuleModel：
FactPattern：
SingleFieldConstraint：
ActionFieldList：




workbench-models-guided-template：提供规则模板
TemplateModel：

方案：后端生成规则json：
		String json = JSONObject.toJSONString(person, new BeforeFilter() {
            @Override
            public void writeBefore(Object object) {
                super.writeKeyValue("xtype", object.getClass().getName());
            }
        });
      前端组装json：
      后端解析json：转成JsonObject循环转换，直接调用JSONObject.parse无法转换（或者看有没有好的办法解析）


      import 一次性全部导入
变量作为leftField
常量作为rightField

urule以jcr的形式所有资源存储为二进制xml；我们是不是也可以存储为json之类呢?增加一个json解析器 动态生成TemplateModel：



决策表：
RuleSet：在这个单元的右边单元中包含ruleset 的名称 和drl文件中的package 是一样
Sequential：值为true 或false，如果是true 则确保规则按照从表格的上面到下面的顺序执行(规则触发是从上朝下，如果是false就是乱序)
Import：导入所需要的引用类或方法，如要导入规则库中的类的列表(逗号隔开)
Functions：紧接右边的单元格可以包含函数，其可用于规则的片断中。Drools 支持在 DRL中定义函数,允许逻辑被嵌入在规则中，不用硬代码改变，小心使用。语法与标准 DRL 相同。有返回值与无返回值两种如果要定义多个函数,就在Functions 后面以逗号作为分隔符 定义多个函数
Variables：紧跟在右边的单元格包含global 声明。格式是类型跟着变量名（全局变量定义，多个用逗号隔开）与drl 中的 global 一个意思
Queries：紧接右边的单元格可以包含 Drools 支持的全局声明。它一个类型，紧跟着一个查询名字（如果需要多个查询，用逗号分隔它们） 与drl 中的query 是一个意思
RuleTable：这个的意思是表示规则名，写法是 在RuleTable 后直接写规则名的前缀，不用另写一列，规则以行号为规则名

CONDITION：指明该列将被用于规则条件 CONDITION （代表条件） 相当于drl 中的when必添，每个规则表至少一个
ACTION：指明该列将被用于推论，简单理解为结果 相当于drl 中的then ACTION 与CONDITION 是平行的，每个规则表至少一个
PRIORITY：指明该列的值将被设置为该规则行的'salience'值。覆盖'Sequential'标志。但注意，如果在ruleSet 下设置了 sequential 的值为true则 PRIORITY 不起作用， 如果 sequential 设置为false 或者不设置 则PRIORITY 生效书写规范 与规则行平行 值 为数值型，
NAME：指明该列的值将被设置为从那行产生的规则的名字。
NO-LOOP：指明这个规则不允许循环。为了这个选项正常运行，这里在该单元格中必须是让该选项生效的一个值（true 或 false）。如果该单元格保持为空，那么这个选项将不会为该行设置
ACTIVATION-GROUP：在这个列中的单元格的值，指出该规则行属于特定的 XOR/活动组。 一个活动组意味着在那个命名组中的规则只有一条会被引发。（即，首条规则引发，中止其他规则活动）。与drl 中的含义是一样的
AGENDA-GROUP：在这个列中的单元格的值， 指出着该规则行属于特定的议程组 可以理解成获取焦点（这是一种在规则组之间的控制流的方法）
RULEFLOW-GROUP：在这个列中的单元格的值， 指出着该规则行属于特定的规则

规则流：工作流+规则整合

DSL（领域语言）：是业务人员通过dslr文件编写的规则文件，业务人员可能不懂技术。通过用文字描述实现业务规则。DSL机制是允许我们定制conditional expressions（条件表达式 LHS）和consequence actions（ 结果值的 也就是 RHS），也可以替换全局变量。

规则模板：规则引擎中，Drools 提供了一个规则模板的概念，规则模板，即规则条件比较值是可变的，且可生成多个规则进行规则调用。规则模板在可以分为两种，第一种为官方上提到的以drt 扩展名+xls(源数据)的方式，第二种为API 模板赋值方式。

事件监听：事件监听有两种方式进行操作，一是通过配置文件，二是通过实现监听的一些接口，配置文件很简单，就是将三大类的监听事件的配置放在spring.xml 或kmodule.xml里就可以了，这三大监听为RuleRuntimeEventListener、AgendaEventListener、ProcessEventListener

WorkBench：创建规则、规则流、决策树、决策表、全局变量、事实等等、、、
整合Java两种方式：
1、配置WorkBench maven仓库，远程拉取jar包执行
2、setting.xml文件配置账户密码，Java中利用release拉取

Kie-Server：
1、与自动扫描大同小异，根据 workbench 构建成jar 通过服务进行启动与访问
2、一个类似 webservice 的web 访问服务器
3、可独立部署的应用服务器
4、可部署集群的服务器应用技术

集群部署：Kie-Server支持集群，WorkBench需要利用ZooKeeper实现,Tomcat下不支持集群

Drools Fusion(CEP  Complex Event Processing)：可以简单的理解为流式数据处理，每条数据看做一个事件，这些事件有时间上的顺序性。

Multithreaded Rule Engine：多线程规则引擎，该功能默认处于关闭状态 drools.multithreadEvaluation = true，目前这样的多线程规则不支持 queries、salience、agenda-group

duplicateRule：存在新旧规则冲突时可配置策略

注意：
1、在取模运算上要注意控制优先级，如： $person: Person((age%2) == 0 && age < 100)
2、memberOf初始化设置，drl文件中更新不起作用（测试结果得知）
3、System.setProperty("drools.dateformat", "yyyy-MM-dd"); // 要在get Service之前设置，如果是spring则需要放入静态块中
4、在drl中动态insert的数据可以通过两种方式获取：
	1）、通过监听器。
	2）、kieSession.getObjects()遍历获取。
5、如果在一个方法中执行多次规则，是要将
原有的fact 对象删除再次inset 才生效，其实这里是有它自己的一些算法的，如果在规则中进行了分组执行焦点这
类，则要注意，删除fact 对象后， 还要再次进行获取焦点或指定分组的操作，否则也不会
执行。
6、规则是需要编译整包发布的，当编辑者修改一条规则后，需要进行Build操作。这是一个对包内规则编译的过程，正确编译后会产出一个kbase文件。 这个kbase文件是个标准的drools可以执行文件。
7、包内的可执行单位是规则流和规则集，而决策表和决策树都是一种特殊形式的规则集。我们提供两种方式执行规则：调用规则流和直接调用规则集
8、使用workbench+kie-server 时要注意，jar 包session 的状态，举例说明：
		有状态Kiesession：有状态的值是相对比较坑的，怎么理解呢，当第一次insert 一个因子后运行的结果是正确的，下一次就与所期待的有所不同了，它会以倍数进行增加，这是什么原因导致的呢？理解起来很简单，因为有状态的session 是迭代方式，会保存上一次的因子在内存中。那我们在做测试时为什么没有这样的问题呢?
			1. 测试过程中，我们每次都是new 一个新的kiesession，所以kiesession 不同，因子也不同
			2. 测试过程中，为了减少内存的消耗，我们一般都通过ksession.dispose();释放，但在kie-server 中是没有这样的操作的
			3. 针对 kie-server 页面的说明，每次启动就是一个新的ksession。但是不没有地方去释放的，除非是重新启动以上就是要注意的点，为什么这里没有说明无状态的情况呢，经过测试后，无状态的方式是不会出现这样的问题的，所以小编这里郑重说明：在调用kie-server 时要采用无状态方式。
9、内存溢出问题：
	1）、如果你的数据在内存使用一次，就不使用了，那么insert完后一定要调用delete方法，再调用dispose方法
	2）、当规则改变时如果需要重新创建会话，记得把之前会话资源释放，调用dispose即可。
10、如果采用变量形式不使用对象.变量，存在的问题是打包测试阶段多个规则集可能使用同一个变量导致测试数据达不到预期效果，解决办法测试传入数据以策略集为维度加上变量参数。

目录结构：
package namespace

import
global
funcation
query
[rule]
EOF

优化：
一、规则语法
1、规则拆分原则：将规则进行拆分，避免出现OR 的情况
2、规则比较原则：将区间或模糊查询的方式排在比较值的后面
3、规则简单原则：尽量避免出现过于复杂比较值
4、规则结果原则：then 中避免出来if eles
二、执行规则
1、创建内存级缓存

应用：
一、如果规则是由开发人员编写，比如编程drl文件，在这种情况下，可以把规则打成jar包，部署到workbench,或者kie-server;
二、如果规则是可配置的，让业务人员来配置，不需要开发人员参与，可以把规则以字符串的形式保存到数据库；
三、最后，小哥还有个重要的看法，就是如果规则是变动频繁的，小哥还是建议使用字符串的方式，对数据库进行增删改操作，如果频繁打包，感觉不太现实，如果您更好的看法可以和小哥讨论，共同进步。



疑问：
1、如何加载Kbase包
我们提供rule-sdk的依赖包，自动完成加载
2、打包发布用到WorkBench了吗
3、新版发布如果有些规则正在执行这个是怎么处理的，继续处理完旧的包？





分享：
1、业界产品对比
2、流模式
3、kjar模式 打成jar包、再引入。
4、提供SDK形式，规则引擎配置规则，下发到各应用