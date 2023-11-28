# easyBlog设计日志

最终的目标，设计出一个页面，能够进行拼接并形成日志

分步进行，

## 设计面板

分为header和main部分

header部分为设置好的部分(设置标题、正文、图片)

main部分，分为两个部分，左边可以对设置的标题正文等进行排序

右边则是显示实时的页面

## 部分前端功能实现

### 实现输入数据，渲染页面

借鉴部分[[Vue框架，字符串的一部分处理为html文档并渲染到页面_vue中输入框最后生成html_Heigl swift的博客-CSDN博客](https://blog.csdn.net/helgeal/article/details/121342176)]

```vue
<template>
<div>
    <el-container>
        <el-header>
            <el-row>
                <el-col :span="6">
                    <div class="grid-content">一级标题</div>
                    <input v-model="h1Text">
    </el-col>
    </el-row>
    </el-header>
        <el-main id="classA">
            <div class="mainR">
                <div v-html="highlight(h1Text)"></div>
    </div>
    </el-main>
    </el-container>
    </div>
</template>
<script setup>
    import { ref } from 'vue';
    const h1Text = ref('')
    function highlight(str) {
        const arr = str.split(h1Text)
        const strHtml = arr.join(`<span style="color: crimson;">${h1Text}</span>`)
        return strHtml
    }
</script>
```

但是上面在进行js之后，发现样式并没有变化。（可能是渲染问题）

```vue
<div v-html="h1Text"></div>
```

上面的代码与js方法后的实现的效果相同

对js部分进行修改

```js
function hightwo(str) {
    const arr = str.split(' ');
    let strHtml = '';
    arr.forEach(word => {
        if (word !== '') {
            strHtml += `<h1>${word}</h1>`;
        }
    });
    return strHtml;
}
```

修改后的代码能够正常显示对应的格式



### 实现自定义渲染页面

  上面已经实现了将输入的数据转换成html格式，但是如果我想要一个标题后加正文，然后再加标题，那么还是会在标题后面添加，而我想在正文的下面添加。

#### 第一步

  我决定将输入数据与上面的按钮等进行分离，决定将单独分开，（选择在header部分添加按钮，点击按钮后，触发新的dialog，在dialog输入数据后，点击确定，能将数据再次传递到header的input中）

```vue
<template>
<el-dialog :modelValue="dialogVisible" title="输入内容" width="30%" @close="handleClose">
    <el-input v-model="inputStr" />
    <template #footer>
        <span class="dialog-footer">
            <el-button type="primary" @click="handleConfirm">确认</el-button>
            <el-button @click="handleClose">取消</el-button>
    </span>
</template>
</el-dialog>
</template>
<script setup>
    import { defineEmits, defineProps, ref } from "vue"
    const props = defineProps(
        {
            dialogVisible: {
                type: Boolean,
                default: false,
                required: true
            }
        }
    )

    const inputStr = ref('')

    const emits = defineEmits(['update:modelValue', 'inputStr'])
    const handleClose = () => {
        emits('update:modelValue', false)
        inputStr.value = '';
    }

    const handleConfirm = () => {
        emits('inputStr', inputStr.value);
        handleClose();
    }
</script>
<style ></style>
```

##### 在子组件中调用defineEmits并定义要发射给父组件的方法

```vue
defineEmits(['update:modelValue', 'inputStr'])
```

(这里定义了update:modelValue和inputStr两个方法)

##### 使用defineEmits会返回一个方法，使用一个变量emits(变量名随意)去接收(选择使用emits接收)

##### 在子组件要触发的方法中，调用emits并传入发射给父组件的方法以及参数

```vue
emits('inputStr', inputStr.value)
```

##### 父组件在子组件标签使用方法

```vue
<Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" @inputStr="inputStr" />

const inputStr = (value) => {
inputValue.value = value;
}v-for
```

定义方法获取子组件传递过来的数据



#### 第二步

  在main的左边部分获取header中input的数据,并进行排序，（后续要添加拖拽功能），将dialog中的inputStr方法进行修改

```js
const handleConfirm = () => {
    emits('inputStr', { inputStr: inputStr.value, title: "一级标题" });
    handleClose();
}
```

  这里传递了两个数据，一个是输入的数据inputStr,一个则是标题（即之后的模板名称,这里先写死）,再调用关闭方法，将输入框清空。

```js
import { ref, reactive } from 'vue';

const listId = ref(0)
const arr = reactive([])
const arrPut = ref('')
const arrTitle = ref('')
const arrNum = ref(0)

const inputStr = (value) => {
    listId.value++;
    const { inputStr, title } = value;

    const newArr = {
        arrPut: inputStr,
        arrTitle: title,
        arrNum: listId.value
    }

    arr.push(newArr);
}
```

  这里定义了一个arr数组，其中有arrPut输入的数据,arrTitle模板的名称,arrNum:数组的id。

定义了一个inputStr的方法：listId.value自增 1，然后从输入的value对象中提取inputStr和title。接着，创建一个新的数组元素对象newArr，其中包含arrPut、arrTitle和arrNum值。最后，使用push方法将newArr添加到arr数组中。

```vue
<div class="mainLFor" v-for="(item, arrNum) in arr" :key="arrNum">
    <span>{{ item.arrTitle }}&nbsp;&nbsp;&nbsp;{{ item.arrPut }}</span>
</div>
```

  通过v-for循环在页面中显示。（需要解决的问题是最多写两个dialog组件，一个用来设计字体，一个用来粘贴图片，那么，字体的dialog需要能够进行共用，需要传递参数才能重复使用,重复使用需要前后端一起）

##### 测试传递数据

  为了解决上面的问题，决定将部分数据从dialog中通过inputStr方法传递到其中（后面可以通过模板的id去查询该模板的css样式，然后在传递到前端）

dialog页面的修改(添加了arrCssL,arrCssR，以及对应的css样式)

```js
const handleConfirm = () => {
    emits('inputStr', { inputStr: inputStr.value, title: "一级标题", arrCssL: 'span style="color: aqua;"', arrCssR: 'span' });
    handleClose();
}
```

主页面的修改(修改了highlight方法，需要左右样式的参数,以及样式的修改；修改了arr数组，将css样式也添加到里面)

```vue
<div class="mainR">
    <div class="mainRFor" v-for="(item, arrNum) in arr" :key="arrNum">
        <div v-html="highlight(item.arrPut, item.arrCssL, item.arrCssR)"></div>
    </div>
</div>
</template>
<script setup>
    import { ref, reactive } from 'vue';

    function highlight(str, left, right) {
        const arr = str.split(' ');
        let strHtml = '';
        arr.forEach(word => {
            if (word !== '') {
                strHtml += `<${left}>${word}</${right}>`;
            }
        });
        return strHtml;
    }

    const listId = ref(0)
    const arr = reactive([])
    const arrPut = ref('')
    const arrTitle = ref('')
    const arrNum = ref(0)
    const arrCssL = ref('')
    const arrCssR = ref('')
    const inputStr = (value) => {
        listId.value++;
        dragIndex++;
        const { inputStr, title, arrCssL, arrCssR } = value;

        const newArr = {
            arrPut: inputStr,
            arrTitle: title,
            arrNum: listId.value,
            arrCssL: arrCssL,
            arrCssR: arrCssR
        }

        arr.push(newArr);
    }
```

##### 测试删除数组

添加删除方法

```js
function removeItem(index) {
    arr.splice(index, 1);
}
```

在循环的页面中进行添加

```vue
<el-button type="danger" @click="removeItem(arrNum)">
    删除
</el-button>
```

#### 第三步

实现拖拽排序 部分代码借鉴[[Vue3中的列表拖拽排序 - 掘金 (juejin.cn)](https://juejin.cn/post/7231519213897875512)]

(列表项拖拽到可放置目标时，将该拖拽的元素从原位置删除，再将拖拽的元素插入到当前可放置目标的位置)

##### 页面部分

```vue
<TransitionGroup name="list" tag="div" class="container">
    <div class="mainLFor" v-for="(item, arrNum) in arr" :key="arrNum" draggable="true"
         @dragstart="dragstart($event, arrNum)" @dragenter="dragenter($event, arrNum)"                   @dragend="dragend" @dragover="dragover">
        <span>
            <el-button type="danger" @click="removeItem(arrNum)">
                <Icon :iconHref="'#icon-shanchu-copy'" />&nbsp;&nbsp;删除
            </el-button>
            {{ item.arrNum }}&nbsp;&nbsp;&nbsp;
            {{ item.arrTitle }}&nbsp;&nbsp;&nbsp;
            {{ item.arrPut }}
        </span>
    </div>
</TransitionGroup>
```

dragover,使用dragover实现的拖拽效果更加直观，拖拽过程就可以显示最终效果;

dragenter,直到鼠标松开时才可以看到拖拽的最终效果

##### js代码部分

```js
let dragIndex = 0
function dragstart(e, index) {
    e.stopPropagation()
    dragIndex = index
    setTimeout(() => {
        e.target.classList.add('moveing')
    }, 0)
}
function dragenter(e, index) {
    e.preventDefault()
    // 拖拽到原位置时不触发
    if (dragIndex !== index) {
        const source = arr[dragIndex];
        arr.splice(dragIndex, 1);
        arr.splice(index, 0, source);
        // 更新节点位置
        dragIndex = index
    }
}
function dragover(e) {
    e.preventDefault()
    e.dataTransfer.dropEffect = 'move'
}
function dragend(e) {
    e.target.classList.remove('moveing')
}
```

  let dragIndex = 0: 定义了一个变量dragIndex，用于记录当前正在拖动的列表项的索引。在拖拽开始时，该变量会被设置为相应的索引。

###### dragstart方法

  e.stopPropagation(): 这行代码阻止了事件冒泡。当事件发生时，它会在元素层次结构中向上传播。在这里，通过调用stopPropagation()方法，可以阻止事件继续传播到其他元素上。
  dragIndex = index: 将传入的索引index赋值给dragIndex。这样，在拖拽过程中，我们就知道是哪一个列表项被拖拽了。

  setTimeout(() => {: 使用setTimeout创建一个异步操作，以在0毫秒后执行一个函数。这里的目的是为了确保在样式更改之前，事件处理程序已经完成了对事件的处理。
  }, 0): 指定延迟时间为0毫秒。这意味着在下一个事件循环中执行异步操作。

  e.target.classList.add('moveing'): 给当前拖拽元素的类名列表添加moveing类。这通常是一个CSS类，用于在拖拽过程中改变元素的样式，使其看起来像正在被拖动。

###### dragenter方法(这个函数是拖拽功能中的一个关键部分，用于处理拖拽元素在目标元素位置上的放置操作)

  e.preventDefault(): 阻止浏览器对事件的默认行为。在这里，我们不希望浏览器对拖拽事件执行任何默认操作，因此通过调用preventDefault方法阻止它。
  if (dragIndex !== index) {: 这是一个条件语句，用于检查当前拖拽元素的索引是否等于目标元素的索引。如果不相等，说明拖拽元素已经移动到了目标元素的位置。
  const source = arr[dragIndex]: 从arr数组中获取拖拽元素的数据。
  arr.splice(dragIndex, 1);: 从arr数组中移除拖拽元素。splice方法的第一个参数是开始移除的元素的索引，第二个参数是要移除的元素数量。这里移除一个元素。
  arr.splice(index, 0, source): 在目标元素的位置插入拖拽元素。splice方法的第一个参数是开始插入的位置，第二个参数是要移除的元素数量（0表示不删除任何元素），第三个参数是要插入的元素（可以使用数组或其他值）。
  dragIndex = index:更新dragIndex变量的值，使其指向目标元素的索引。

###### dragover方法(这个函数在拖拽过程中会被调用，主要用于设置拖拽事件的视觉效果)

  e.preventDefault(): 阻止浏览器对事件的默认行为。在这里，我们不希望浏览器对拖拽事件执行任何默认操作，因此通过调用preventDefault方法阻止它。
  e.dataTransfer.dropEffect = 'move': 设置拖拽事件的视觉效果。dataTransfer对象是拖拽事件相关的数据存储对象，其中dropEffect属性用于控制拖拽元素在目标元素上移动时的视觉效果。设置为'move'表示在拖拽过程中，拖拽元素会有一个移动的动画效果。

###### dragend方法(这个函数在拖拽结束时会被调用，主要用于清除拖拽过程中对目标元素的样式更改)

  e.target.classList.remove('moveing'): 从目标元素上移除moveing类。在拖拽过程中，目标元素可能会被添加了一个moveing类，用于指示它是否处于拖拽状态。在拖拽结束时，我们需要将这个类移除，以恢复目标元素的原始样式。

##### 修改传递数据部分代码

###### 前端页面

```vue
<el-header style="height: 60px;">
    <el-scrollbar>
        <div class="scrollbar-flex-content">
            <div v-for="(item, index) in modelList" :key="index" class="scrollbar-item">
                <div @click="handleDialogValue(item.modelTitle, item.id)">
                    {{ item.modelTitle }}
                </div>
            </div>
        </div>
    </el-scrollbar>
</el-header>
<Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :dialogTitle="dialogTitle"
        :dialogModelId="dialogModelId" @inputStr="inputStr" />
</div>
</template>

<script setup>
    import requestUtil from "@/util/request";
    import Dialog from '../components/blog/dialog.vue'

    const dialogVisible = ref(false)
    const dialogTitle = ref('')
    const dialogModelId = ref(0)
    const handleDialogValue = (title, modelId) => {
        dialogTitle.value = title;
        dialogModelId.value = modelId;
        dialogVisible.value = true
    }
```

将modelTitle（模板标题）, id（模板id）两个数据传递到dialog中

###### dialog页面的修改

```vue
<template>
<el-dialog :modelValue="dialogVisible" :title="dialogTitle" width="30%" @close="handleClose">
    <el-input v-model="inputStr" />
    <template #footer>
        <span class="dialog-footer">
            <el-button type="primary" @click="handleConfirm">确认</el-button>
            <el-button @click="handleClose">取消</el-button>
    </span>
</template>
</el-dialog>
</template>
<script setup>
    import { defineEmits, defineProps, ref } from "vue"
    import { ElMessage } from "element-plus";
    const props = defineProps(
        {
            dialogTitle: {
                type: String,
                default: '',
                required: true
            },
            dialogModelId: {
                type: Number,
                default: -1,
                required: true
            },
            dialogVisible: {
                type: Boolean,
                default: false,
                required: true
            }
        }
    )

    const handleConfirm = () => {
        // 检查 inputStr 是否为空
        if (!inputStr.value) {
            ElMessage.warning('请输入内容');
            return;
        }

        emits('inputStr', { inputStr: inputStr.value, title: props.dialogTitle, modelId: props.dialogModelId });
        handleClose();
    }
```

获取modelTitle（模板标题）, id（模板id）两个数据，标题在dialog中显示，同时在传递到页面中

###### 前端页面js

```js
const listId = ref(0)
const arr = reactive([])
const halfArr = reactive([])
const arrPut = ref('')
const arrTitle = ref('')
const arrNum = ref(0)
const arrCssL = ref('')
const arrCssR = ref('')
const inputStr = (value) => {
    listId.value++;
    dragIndex++;

    const { inputStr, title, modelId } = value;
    halfArr.value = modelList.value.find((model) => model.id === modelId);

    const newArr = {
        arrNum: listId.value,
        arrTitle: title,
        arrModelId: modelId,
        arrCssL: halfArr.value.cssLeft,
        arrPut: inputStr,
        arrCssR: halfArr.value.cssRight,
    }
    arr.push(newArr);
}
```

  使用find方法在modelList数组中查找与modelId（模板id）匹配的数据。如果找到了，将该组数据赋给halfArr。创建一个新的数组newArr，将数据赋值给该数组，并将该数组添加到arr数组中



## 设计数据库表格

目前思路是用户表，模板表，文档表

模板表（需要模板对应id,模板名称,css样式的左半边,css样式的右半边,后续可能提供的改变颜色等，因此css样式的属性可能会被拆分）

文档表（需要文档id，文档的标题，文档的作者，判断是否为草稿，判断是否为仅自己可见，创建时间，更新时间）

用户表(用户id,账号,头像,昵称,密码，盐值，状态，创建时间，最后登录时间)

排序表(用户id，博客id，顺序)

组建表(博客id，模板id，顺序，模板内容)

(测试使用python接口)气象表



## 创建后端项目

### 修改pom文件

### 修改yml文件

```yaml
server:
  port: 8126
  servlet:
    context-path: /

spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_easyblog?useSSL=FALSE&serverTimezone=Asia/Shanghai
    username: root
    password: 123456

mybatis-plus:
  global-config:
    db-config:
      id-type: auto
  configuration:
    map-underscore-to-camel-case: true
    auto-mapping-behavior: full
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:mapper/*.xml
```



### 简单案例



## 前后端交互

V 1.0

目前遇到的问题：在css格式第3个模板无法使用，会报错。

推测可能是：我有段数据 span style="color: aqua;"这里使用了""符号，解决特殊字符引起json解析错误，关于单双引号的转义问题。

结果：数组传递的时候，将模板的id当作数组下标传递了，因为没有第4个模板，所以报错了



### 后端接收前端数据测试

#### 前端提交接口

```js
const submitArr = async () => {

    if (!arr || arr.length === 0) {
        ElMessage.warning('请先添加博客内容');
        return;
    }

    const categoryModelArr = arr.map((item, index) => ({
        categoryId: 1001,
        modelId: item.arrModelId,
        modelSort: index,
        content: item.arrPut,
    }));

    let result = await requestUtil.post("CategoryModel/save", categoryModelArr);
    let data = result.data;
    if (data.code == 200) {
        ElMessage.success(data.msg)
    } else {
        ElMessage.error(data.msg)
    }
}
```

这里封装了一个新的数组categoryModelArr，用于传递数据，categoryModelArr中每一组数据都是后端categoryModel实体类的属性（添加默认博客id，categoryId: 1001）

#### 后端测试接口

```java
/**
     * @description: 测试接收前端的数据
     * @author yaol
     * @date 2023/9/3 14:07
     * @version 1.0
     */
@PostMapping("/test")
public void test(@RequestBody List<CategoryModel> categoryModelArr){

    System.out.println("我被触发了");

    for (CategoryModel categoryModel : categoryModelArr) {
        System.out.println("category_id: " + categoryModel.getCategoryId());
        System.out.println("model_sort: " + categoryModel.getModelSort());
        System.out.println("model_id: " + categoryModel.getModelId());
        System.out.println("content: " + categoryModel.getContent());
        System.out.println("-------------------------");
    }
}
```



#### 提交数据部分测试

遇到的问题

##### network问题

前端传递/打印数据的方法

let result = await requestUtil.post("Category/addCategory?" + JSON.stringify(catagoryForm.value))

console.log(JSON.stringify(catagoryForm.value))

前端打印的数据

{"sort":3,"cover":"ccc","categoryName":"q","categoryBrief":"aaa","categorySelf":"1","userId":100000}

后端接收时的报错

Invalid character found in the request target [/Category/addCategory?{%22sort%22:3,%22cover%22:%22www%22,%22categoryName%22:%22a%22,%22categoryBrief%22:%22qq%22,%22categorySelf%22:%221%22,%22userId%22:100000} ]



解决方法：修改前端的请求路径

let result = await requestUtil.post("Category/addCategory", catagoryForm.value);



##### 前端上传封面问题

拿了之前项目的上传头像，稍微修改了一下

###### 前端代码

```vue
<el-form-item prop="cover" class="el-form-item coverItem">
    <el-form ref="coverUpload" class="el-form elCoverForm" :model="coverForm" label-width="100px">
        <div class="coverItemText">请上传博客封面</div>
        <el-upload class="avatar-uploader" :action="getServerUrl() + 'Category/uploadCover'"
                   :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
                <Plus />
            </el-icon>
        </el-upload>
        <el-button class="coverItemBtn" @click="handleConfirm">确认上传</el-button>
    </el-form>
    <el-input v-model="catagoryForm.cover" type="hidden"></el-input>
</el-form-item>
</el-form>
</div>
</template>
<script setup>
    import requestUtil, { getServerUrl } from "@/util/request";
    import Icon from '@/components/Icon.vue';
    import { ref, reactive, onMounted } from 'vue';
    import store from "@/store";
    import { ElMessage, ElLoading, ElMessageBox } from "element-plus";
    import router from '@/router';
    import { useRouter } from "vue-router";
    import { Plus } from '@element-plus/icons-vue';

    /**上传封面类 */
    const imageUrl = ref("")
    const coverUpload = ref(null)
    const coverForm = ref({
        cover: ''
    })

    imageUrl.value = getServerUrl() + 'images/cover/' + coverForm.value.cover;

    const handleAvatarSuccess = (res) => {
        imageUrl.value = getServerUrl() + res.data.src
        coverForm.value.cover = res.data.title;
    }

    const beforeAvatarUpload = (file) => {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isJPG) {
            ElMessage.error('图片必须是jpg格式')
        }
        if (!isLt2M) {
            ElMessage.error('图片大小不能超过2M!')
        }
        return isJPG && isLt2M
    }

    const handleConfirm = async () => {
        catagoryForm.value.cover = coverForm.value.cover;
    }
```

  el-upload中的action后面为请求路径;show-file-list为是否显示已上传文件列表;on-success为文件成功上传后;

before-upload为文件上传之前。这里在文件上传前对文件的类型进行判断需要是jpg格式且大小不超过2M。

###### 后端接收代码

 在控制器中添加uploadCover方法

 先将文件复制到指定的位置，然后向前端返回标题和路径

```java
@Value("${coverImagesFilePath}")
private String coverImagesFilePath;

@RequestMapping("/uploadCover")
public Map<String,Object> uploadCover(MultipartFile file)throws Exception{
    Map<String,Object> resultMap=new HashMap<>();
    if(!file.isEmpty()){// 获取文件名
        String originalFilename = file.getOriginalFilename();
        String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName= "cover_"+DateUtil.getCurrentDateStr()+suffixName;
        FileUtils.copyInputStreamToFile(file.getInputStream(),new File(coverImagesFilePath+newFileName));
        resultMap.put("code",0);
        resultMap.put("msg","上传成功");
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("title",newFileName);
        dataMap.put("src","images/cover/"+newFileName);
        resultMap.put("data",dataMap);
    }
    return resultMap;
}
```

yml文件中需要添加路径

```yaml
coverImagesFilePath: E://IdeaProject/easyBlog/src/main/resources/images/cover/
```

在WebAppConfigurer添加代码（否则会导致前端无法获取图片）

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) { registry.addResourceHandler("/images/cover/**").addResourceLocations("file:E:\\IdeaProject\\easyblog\\src\\main\\resources\\images\\cover\\");
}
```



## 补全功能

### 博客路由守卫

在router下创建permission.js

这里根据store里的userInfo判断，如果userInfo为空，则没有登录，会跳转到登录界面

```js
import router from "@/router/index"
import store from "@/store"
import ElMessage from 'element-plus'
router.beforeEach((to, from, next) => {
    const whiteList = ['/login'] // 白名单
    let userInfo = store.getters.GET_USERINFO;
    if (userInfo) {
        next();
    } else {
        if (whiteList.includes(to.path)) {
            next();
        } else {
            ElMessage("请先登录")
            next("/login");
        }
    }
})
```

添加到main.js中

```js
import '@/router/permission.js'
```

在测试中发现路由守卫失效，在控制台打印时，发现在index.js中，为了保证登录后能获取到信息(不需要刷新登录)，为userInfo设置了默认值，因此在启动项目时，由于usrInfo有默认值，导致无法自动跳转到登录页面，所以对userInfo为空的判断进行修改

```js
JSON.stringify(userInfo) != "{}"
```

### 注册界面验证码

选择在前端显示验证码，然后进行表单提交时，判断验证码是否一致，如果一致则提交到后端，不一致则取消提交

创建canvas7.vue页面，用于显示验证码(代码部分来源[[vue实现登录验证码_vue.js_脚本之家 (jb51.net)](https://www.jb51.net/article/219545.htm)])

将上面的代码进行了修改：

首先将代码修改为了vue3的（原代码为vue2中的）

然后在对代码进行测试的时候，发现每次点击更新代码后，验证码图片不会改变，需要验证码输入框验证后才会改变，这很影响使用，因此要进行修改。

原代码中是在父组件页面生成一串随机数identifyCode，然后传到子组件中，子组件通过对identifyCode的值进行监听，如果发生改变，则重新执行drawPic画图方法（即生成新的验证码图片）

解决方法:

1. 将生成随机数identifyCode和drawPic画图方法都移动到子组件。

2. 通过父组件传值，让它能够重新生成随机数和画图。

3. 将子组件生成的验证码传递到父组件，用于表单的验证。

   通过父组件传值，让它能够重新生成随机数和画图

父组件代码：

```js
import { provide } from 'vue'
let count = ref(0)
provide('countNum', count)

onMounted(() => {
  count.value++;
})

function clickRefreshCode() {
  count.value++;
}
```

父组件中使用provide，该组件能够跨组件进行数据的传递，这里提供了一个名为countNum的ref对象，值为count，

在每次页面加载和点击刷新验证码图片时count的值会加1

子组件代码:

```js
import { inject } from 'vue';
let flagNum = inject('countNum')

watch(flagNum, (newValue, oldValue) => {
  if (newValue > 0) {
    flagNum.value--;
  }
})
```

子组件中使用inject接收父组件提供的数据，这里对flagNum进行监听，如果flagNum的值变化，就执行watch内的方法（即生成随机数和画图）,同时flagNum的值减1（这是为了防止flagNum过于大），再在外面包一层if判断只有flagNum的值大于0的时候才会进行上面的操作，如果没有该判断的话会一直重复循环下去。

将子组件生成的验证码传递到父组件，用于表单的验证

```js
import { defineEmits } from 'vue';

const emits = defineEmits(['update:identifyCode'])

watch(flagNum, (newValue, oldValue) => {
  if (newValue > 0) {
    refreshCode();
    emits('update:identifyCode', identifyCode);
    flagNum.value--;
  }
})
```

使用defineEmits定义了update:identifyCode方法

emits触发update:identifyCode的方法，同时将identifyCode（生成的随机数）作为参数传递过去

父组件页面

```vue
<template>
  <Identify class="codePic" @update:identifyCode="updateIdentifyCode"@click="clickRefreshCode()"></Identify>
</template>

<script setup>
import Identify from '../components/canvas7.vue'
let currentCode = ref('')

function updateIdentifyCode(newIdentifyCode) {
  currentCode.value = newIdentifyCode;
}
</script>
```

定义currentCode用于存储验证码

@update:identifyCode="updateIdentifyCode" 用于监听一个名为 update:identifyCode 的自定义事件。当这个事件被触发时，会将事件参数传递给 updateIdentifyCode 函数。

updateIdentifyCode方法用于更新验证码的值。



完整canvas7.vue页面

```vue
<template>
  <div class="s-canvas">
    <canvas id="s-canvas" :width="contentWidth" :height="contentHeight"></canvas>
  </div>
</template>
<script setup>
import { ref, watch, onMounted, defineProps, inject, defineEmits } from 'vue';

const identifyCodes = '1234567890abcdefjhijklinopqrsduvwxyz'
let identifyCode = ref('1234')
// flagNum接收变量(inject组件跨级传值)
let flagNum = inject('countNum')
const emits = defineEmits(['update:identifyCode'])

const props = defineProps({
  fontSizeMin: {
    type: Number,
    default: 25
  },
  fontSizeMax: {
    type: Number,
    default: 35
  },
  backgroundColorMin: {
    type: Number,
    default: 200
  },
  backgroundColorMax: {
    type: Number,
    default: 220
  },
  dotColorMin: {
    type: Number,
    default: 60
  },
  dotColorMax: {
    type: Number,
    default: 120
  },
  contentWidth: {
    type: Number,
    default: 90
  },
  contentHeight: {
    type: Number,
    default: 38
  }
})

const randomNum = (min, max) => {
  return Math.floor(Math.random() * (max - min) + min)
}

// 生成一个随机的颜色
const randomColor = (min, max) => {
  let r = randomNum(min, max)
  let g = randomNum(min, max)
  let b = randomNum(min, max)
  return 'rgb(' + r + ',' + g + ',' + b + ')'
}

const drawPic = (identifyCode) => {
  let canvas = document.getElementById('s-canvas')
  let ctx = canvas.getContext('2d')
  ctx.textBaseline = 'bottom'
  // 绘制背景
  ctx.fillStyle = '#e6ecfd'
  ctx.fillRect(0, 0, props.contentWidth, props.contentHeight)
  // 绘制文字
  for (let i = 0; i < identifyCode.length; i++) {
    drawText(ctx, identifyCode[i], i)
  }
  drawLine(ctx)
  drawDot(ctx)
}

const drawText = (ctx, txt, i) => {
  ctx.fillStyle = randomColor(50, 160) // 随机生成字体颜色
  ctx.font = randomNum(props.fontSizeMin, props.fontSizeMax) + 'px SimHei' // 随机生成字体大小
  let x = (i + 1) * (props.contentWidth / (identifyCode.length + 1))
  let y = randomNum(props.fontSizeMax, props.contentHeight - 5)
  var deg = randomNum(-30, 30)
  // 修改坐标原点和旋转角度
  ctx.translate(x, y)
  ctx.rotate(deg * Math.PI / 180)
  ctx.fillText(txt, 0, 0)
  // 恢复坐标原点和旋转角度
  ctx.rotate(-deg * Math.PI / 180)
  ctx.translate(-x, -y)
}

const drawLine = (ctx) => {
  // 绘制干扰线
  for (let i = 0; i < 4; i++) {
    ctx.strokeStyle = randomColor(100, 200)
    ctx.beginPath()
    ctx.moveTo(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight))
    ctx.lineTo(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight))
    ctx.stroke()
  }
}

const drawDot = (ctx) => {
  // 绘制干扰点
  for (let i = 0; i < 30; i++) {
    ctx.fillStyle = randomColor(0, 255)
    ctx.beginPath()
    ctx.arc(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight), 1, 0, 2 * Math.PI)
    ctx.fill()
  }
}

// 重置验证码
const refreshCode = () => {
  identifyCode = '';
  makeCode(identifyCodes, 4);
  drawPic(identifyCode);
}
const makeCode = (o, l) => {
  for (let i = 0; i < l; i++) {
    identifyCode += identifyCodes[randomNumCode(0, identifyCodes.length)]
  }
}
const randomNumCode = (min, max) => {
  return Math.floor(Math.random() * (max - min) + min)
}

// 替换`watch`部分
watch(flagNum, (newValue, oldValue) => {
  if (newValue > 0) {
    refreshCode();
    emits('update:identifyCode', identifyCode);
    flagNum.value--;
  }
})

// 替换`mounted`生命周期钩子
onMounted(() => {
  refreshCode();
})
</script>
```

### 注册功能实现

前端提交

```js
const handleRegister = () => {
  registerRef.value.validate(async (vaild) => {
    if (vaild) {
      if (registerForm.value.code !== currentCode.value) {
        ElMessage.error('请填写正确验证码')
        refreshCode()
      } else {
        let result = await requestUtil.post("user/register", registerForm.value);
        let data = result.data;
        if (data.code == 200) {
          ElMessage.success("注册成功");
          router.push('/login')
        } else {
          ElMessage.error(data.msg);
        }
        console.log("表单中数据为" + qs.stringify({ username: registerForm.value.username, password: registerForm.value.password }))
      }
    } else {
      console.log("注册数据不正确")
    }
  })
}
```

后端在userServiceImpl中创建register方法

这里对密码进行加密，用户注册的时候会随机生成盐值

String salt = UUID.randomUUID().toString().toUpperCase();生成一个随机的UUID，toUpperCase()方法将字符串全部转换为大写字母。

DigestUtils.md5DigestAsHex对密码(盐值+密码+盐值)进行加密,加密3次。

```java
/**
     * 执行密码加密
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    /**
     * @description: 用户注册(密码加密)
     * @author yaol
     * @date 2023/11/3 22:48
     * @version 1.0
     */
    @Override
    public void register(User user) {
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }
```

在congtroller中调用register方法

```java
@PostMapping("/register")
public R register(@RequestBody User user){
    userService.register(user);
    return R.ok();
}
```

### 退出功能

退出到登录界面，同时将session清除

前端页面(上方布局)

```vue
<el-button @click="logout()">退出登录</el-button>
<script setup>
const logout = () => {
  store.dispatch('logout')
  ElMessage.success("退出成功!");
}
</script>
```

stroe中index.js

```js
      actions: {
        logout() {
          window.sessionStorage.clear();
          router.replace("/login")
        }
      },
```

### 显示头像

在登录后页面显示用户的头像，如果没有修改则为默认的头像

前端

```vue
<template>
   <el-avatar shape="square" :size="40" :src="squareUrl" />
</template>

<script setup>
import Icon from "@/components/Icon.vue";
import { ref, reactive, onMounted } from "vue";
import requestUtil, { getServerUrl } from '@/util/request'
import store from "@/store";
import router from "@/router";

let currentUser = ref(store.getters.GET_USERINFO);

let squareUrl = ref(getServerUrl() + 'images/avatar/' + currentUser.value.avatar)
</script>
```

此时图片的请求路径为

```js
http://localhost:8126/images/avatar/default.jpg
```

这里我们在后端的src的main下resources创建images文件夹，再在里面1创建avatar文件夹，存放一张default.jpg的图片

修改后端的WebAppConfigurer(即用于解决跨域问题的,implements WebMvcConfigurer 的文件)

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) { registry.addResourceHandler("/images/avatar/**").addResourceLocations("file:E:\\IdeaProject\\easyblog\\src\\main\\resources\\images\\avatar\\");
}
```

addResourceHandler处为请求路径，addResourceLocations处为映射到硬盘中的位置

### 查询博客界面



### 添加更新状态

前端页面

```vue
<el-table-column prop="categoryType" label="查看权限" width="200" min-width="200" align="center">
    <template v-slot="{ row }">
<el-switch v-model="row.categoryType" @change="categoryTypeChangeHandle(row)" active-text="共享"
           inactive-text="仅自己可见" active-value="0" inactive-value="1">
        </el-switch>
    </template>
</el-table-column>
```

active-text属性为switch打开时的文字描述;active-value属性为switch状态为 on 时的值。

前端代码

```js
const categoryTypeChangeHandle = async (row) => {
    let res = await
    requestUtil.get("Category/updateCategoryType/" + row.categoryId + "/categoryType/" + row.categoryType);
    if (res.data.code == 200) {
        ElMessage({
            type: 'success',
            message: '执行成功!'
        })
    } else {
        ElMessage({
            type: 'error',
            message: res.data.msg,
        })
        getCategoryList();
    }
}
```

后端代码

```java
/**
     * 更新博客的查看权限
     * @param categoryId
     * @param categoryType
     * @return
     */
@GetMapping("/updateCategoryType/{categoryId}/categoryType/{categoryType}")
public R updateCategoryType(@PathVariable(value = "categoryId")Integer categoryId
                            ,@PathVariable(value = "categoryType")String categoryType){
    Category currentCategory = categoryService.getById(categoryId);
    currentCategory.setCategoryType(categoryType);
    currentCategory.setUpdateTime(new Date());
    categoryService.saveOrUpdate(currentCategory);
    return R.ok();
}
```

这里前端传递了对应博客的id和状态值，传递到后端，后端根据获取的id找到对应博客，然后更新博客的查看权限和最后的更新时间。



### 实现表格行的单独拖拽

教程参考[[Vue3中的列表拖拽排序 - 掘金 (juejin.cn)](https://juejin.cn/post/7231519213897875512)]

#### 安装 运行依赖 sortablejs

在vue ui中进行安装

#### 引入sortablejs

```vue
import Sortable from 'sortablejs';
```

(可能有警告，找不到文件，但是不影响使用，可以使用cnpm安装)

#### 使用sortablejs

##### 前端代码

```js
function setSort() {
    const el = document.querySelector('#categoryTable table tbody')
    new Sortable(el, {
        sort: true,
        ghostClass: 'sortable-ghost',
        onEnd: async (e) => {
            const targetRow = categoryVoList.value.splice(e.oldIndex, 1)[0]
            categoryVoList.value.splice(e.newIndex, 0, targetRow)
            // 更新sort属性
            categoryVoList.value = categoryVoList.value.map((item, index) => ({ ...item, sort: index + 1 }));
            let result = await requestUtil.post("Category/updateVoList", categoryVoList.value);
            let data = result.data;
            if (data.code == 200) {
                ElMessage.success(data.msg);
            } else {
                ElMessage.error(data.msg)
            }
        },
    })
}
```

部分讲解：[document.querySelector('#categoryTable table tbody')]

1.#dragTable：这是一个ID选择器，用于匹配ID属性为"dragTable"的HTML元素。在HTML文档中，ID应该是唯一的，因此这个选择器会找到唯一一个ID为"dragTable"的元素。
2.table：这是一个元素选择器，用于匹配<table>元素。在当前上下文中，这是指<dragTable>元素内的<table>元素（因为上面已经限定了#dragTable）。
3.tbody：这是一个元素选择器，用于匹配<tbody>元素。这个选择器会找到<dragTable>元素内的<table>元素内的<tbody>元素。

  onEnd：函数，当拖拽操作结束时触发。在onEnd函数中，首先通过splice方法移除了旧的元素，然后将旧的元素添加到新的位置。接着，使用map方法遍历分类列表，并更新每个元素的sort属性。

  const targetRow = categoryVoList.value.splice(e.oldIndex, 1)[0]这行代码是从分类列表中移除位于e.oldIndex位置的元素。splice方法的第一个参数是移除元素的开始位置，第二个参数是移除元素的数量。该方法返回了一个数组，包含了从分类列表中移除的元素。只取了数组的第一个元素，并将其赋值给targetRow。接下来，categoryVoList.value.splice(e.newIndex, 0, targetRow)这行代码是将targetRow元素插入到分类列表的e.newIndex位置。splice方法的第一个参数是插入位置的索引，第二个参数是0（表示不删除任何元素），第三个参数是插入的元素。然后，通过map方法更新了分类列表中每个元素的sort属性。categoryVoList.value.map((item, index) => ({ ...item, sort: index + 1 }))这行代码遍历了分类列表中的每个元素，并为每个元素添加了一个sort属性，属性的值为元素在分类列表中的索引加1。

##### 后端代码

在后端对数组进行更新

```java
@PostMapping("/updateVoList")
public R categoryUpdateVoList(@RequestBody List<CategoryVo> categoryVoList){
    for(CategoryVo categoryVo:categoryVoList){
        Category newCategory = new Category();
        newCategory.setCategoryId(categoryVo.getCategoryId());
        newCategory.setSort(categoryVo.getSort());
        boolean flag = categoryService.updateById(newCategory);
        if(!flag){
            return R.error("修改排列顺序失败!");
        }
    }
    return R.ok("修改排列顺序成功!");
}
```

  执行updateById操作，Mybatis-Plus会先解析该实体类对应的表名和主键名，然后根据传入的对象获取更新的字段名和值，最后根据主键ID值生成一条更新SQL语句

##### 前后交互之后

  更新数据库的数组后，重新使用getCategoryList();方法获取数组时，发现有一个小bug，那就是页面的顺序没有更新，但是鼠标移动到数组的时候，如果该数组之前被拖拽到别的位置，那么对应位置会出现悬停标志（即数组的下标与数组的排序不一致）。

```js
let loginLoading = ElLoading.service({
    lock: true,
    text: '序列保存中...',
    background: 'rgba(0, 0, 0, 0.7)',
    timer: 3000  // 动画时长为3s
});
categoryVoList.value = [];
getCategoryList();
loginLoading.close();
```

  选择将页面的数组数据进行清空，同时再次请求获取数组。(同时为了解决闪屏的问题，加入了动画)

### 实现简单置顶功能

#### 前端代码(参考来源[https://blog.csdn.net/u014643282/article/details/100576321*/])

```vue
<el-button v-if="scope.row.sort != 1" type="primary" @click="handleListTop(scope.$index)">
    <Icon :iconHref="'#icon-bianji-copy'" />&nbsp;&nbsp;置顶
</el-button>
```

#### 前端js

```js
async function handleListTop(index) {
    categoryVoList.value.unshift(categoryVoList.value[index])
    categoryVoList.value.splice(index + 1, 1)
    categoryVoList.value = categoryVoList.value.map((item, index) => ({ ...item, sort: index + 1 }));
    let result = await requestUtil.post("Category/updateVoList", categoryVoList.value);
    let data = result.data;
    if (data.code == 200) {
        getCategoryList();
        ElMessage.success("修改置顶成功！");
    } else {
        ElMessage.error("修改置顶失败")
    }
    console.log(categoryVoList.value)
}
```

  **unshift(): 将一个元素添加到数组的开头。这里将对应的一组数据添加到数组的开头;**

  **splice(): 从数组中删除一个元素。因为我们之前先在数组的开头添加了一组数据，所以原数据在新数组的（index + 1）的位置，所以我们要将这个数据删除，否则会出现重复的数据;**

  map(): 创建一个新的数组，将每组数据的下标加一赋值给sort。

最后将新数组交给后端，后端对数据库进行更新。

### 完善添加博客的功能



```java
@GetMapping("/setCategorySelf/{categoryId}")
public R setCategorySelf(@PathVariable(value = "categoryId")Integer categoryId){
    Category currentCategory = categoryService.getById(categoryId);
    currentCategory.setCategorySelf("1");
    categoryService.saveOrUpdate(currentCategory);
    return R.ok();
}
```

将文章的属性设置为博客

### 删除博客

#### 后端

```java
@Transactional
@PostMapping("/delete")
public R delete(@RequestBody Integer[] ids){
    categoryService.removeByIds(Arrays.asList(ids));
    categoryModelService.remove(new QueryWrapper<CategoryModel>().in("category_id",ids));
    return R.ok();
}
```

  @Transactional注解，事务正常起作用。无异常时正常提交，有异常时数据回滚

  Arrays.asList将传递进来的ids数组转换成List。

  mybatis plus 对应的service 接口中有个removeByIds() 接口，来删除多个数据，按照id 进行删除，我们可以将要删除的id 放入List集合中。

### 修改博客

#### 后端

将前端传递过来的部分数据封装到category中，并使用saveOrUpdate更新数据库。

```java
@PostMapping("/updateCategory")
public R updateCategory(@RequestBody CategoryAddVo categoryAddVo){
    Category category = new Category();
    category.setCategoryId(categoryAddVo.getCategoryId());
    category.setSort(categoryAddVo.getSort());
    category.setCover(categoryAddVo.getCover());
    category.setCategoryName(categoryAddVo.getCategoryName());
    category.setCategoryBrief(categoryAddVo.getCategoryBrief());
    category.setUserId(categoryAddVo.getUserId());
    String username = userService.getUsernameById(categoryAddVo.getUserId());
    category.setUsername(username);
    category.setUpdateTime(new Date());
    categoryService.saveOrUpdate(category);
    return R.ok();
}
```

#### 前端（添加博客信息页面）

##### 出现的最大问题（或许能够进行修改）

在store中的index.js文件中使用了下面的代码

```vue
state: {
    newCategoryId: sessionStorage.getItem("newCategoryId") || null
  },
  getters: {
    GET_NEWCATRGORYID: state => {
      return state.newCategoryId;
    }
  },
  mutations: {
    SET_NEWCATRGORYID: (state, newCategoryId) => {
      sessionStorage.setItem("newCategoryId", newCategoryId);
    }
  }
```

  当前端使用`const theCategoryId = ref(store.getters.GET_NEWCATRGORYID);`接收的时候，会出现问题，刚加载进入页面时，无法获取到对应的值，即store.getters.GET_NEWCATRGORYID的值为null。需要进行页面刷新才能够使用。

  于是对它进行了修改:

```vue
GET_NEWCATRGORYID: state => {
      return state.newCategoryId || sessionStorage.getItem("newCategoryId");
    }
```

  使用上面的代码后，发现加载进入页面后能获取到值，但仍有一个缺点，就是state.newCategoryId在不刷新页面的情况下它的值始终为第一次加载进入页面的值。

  添加了下面的方法:

```
  mutations: {
    RESET_NEWCATRGORYID: (state, rightId) => {
      state.newCategoryId = rightId;
    }
  },
```

  在页面中对store.getters.GET_NEWCATRGORYID和sessionStorage.getItem("newCategoryId")的值进行比较，如果不相等，则将session中的值赋给store。

  下面是页面的代码:

跳转前的页面，将对应的博客id设置到session中。

```js
const handleCatrgoryValue = (id) => {
    store.commit('SET_NEWCATRGORYID', id);
    router.push('/blogAdd');
}
```

跳转后的页面，根据store中的值进行判断,如果store中的值与session中的值不同，则修改store中的值,最后将值赋给theCategoryId。然后对theCategoryId进行判断，如果theCategoryId为null（即新增）,那么新增一个博客，并将博客的id设置到session中，如果不为null，则获取数据库中对应的博客数据:

```js
const getCurrentCategory = async () => {

    if (store.getters.GET_NEWCATRGORYID != sessionStorage.getItem("newCategoryId")) {
        store.commit('RESET_NEWCATRGORYID', sessionStorage.getItem("newCategoryId"));
    }
    const theCategoryId = ref(store.getters.GET_NEWCATRGORYID);
    console.log("加载之前的theCategoryId:" + theCategoryId.value);
    catagoryForm.value.userId = currentUser.value.userId;

    if (theCategoryId.value === null || theCategoryId.value === undefined) {
        // 添加新的博客，所以要获取新的id
        let result = await requestUtil.post("Category/addCategory");
        if (result.data.code == 200) {
            let newId = result.data.categoryId;
            let newSort = result.data.newSort;
            store.commit('SET_NEWCATRGORYID', newId);
            catagoryForm.value.categoryId = newId;
            catagoryForm.value.sort = newSort;
        }
    } else {
        // 修改博客，获取对应博客的数据
        let response = await requestUtil.post("Category/findCategory/" + theCategoryId.value);
        catagoryForm.value = response.data.currentCategory;
        catagoryForm.value.categoryId = theCategoryId.value;
        coverForm.value.cover = catagoryForm.value.cover;
        imageUrl.value = getServerUrl() + 'images/cover/' + coverForm.value.cover;
    }
}

onMounted(getCurrentCategory)
```

#### 前端（补全博客数据页面）

  该页面用于添加博客的内容，需要获取数据库中存储的博客数据（如果是选择编辑博客的话）。

这里将当前的博客id传递到后端，获取模板数据，并将模板数据与模板信息数据相结合。

```js
const listId = ref(0)
const theCategoryId = ref();

const getModelList = async () => {

    if (store.getters.GET_NEWCATRGORYID != sessionStorage.getItem("newCategoryId")) {
        store.commit('RESET_NEWCATRGORYID', sessionStorage.getItem("newCategoryId"));
    }
    theCategoryId.value = store.getters.GET_NEWCATRGORYID;
    console.log(theCategoryId.value)

    try {
        const response = await requestUtil.get("blogModel/list");
        modelList.value = response.data.modelList;
        /**重新拼接(获取之前的博客内容) */
        const result = await requestUtil.post("CategoryModel/list/" + theCategoryId.value);
        categoryModelList.value = result.data.categoryModelList;

        categoryModelList.value.forEach((item, index) => {
            listId.value++;
            const targetModel = modelList.value.find(model => model.id === item.modelId);
            if (targetModel) {
                arr.push({
                    arrNum: listId.value,
                    arrTitle: targetModel.modelTitle,
                    arrModelId: item.modelId,
                    arrCssL: targetModel.cssLeft,
                    arrPut: item.content,
                    arrCssR: targetModel.cssRight,
                });
            }
        });
    } catch (error) {
        console.error(error)
    }
}

onMounted(getModelList)
```

  遍历categoryModelList，使用find方法查找modelList中id与当前item的modelId相等的模型（即遍历携带文本的内容，与模板信息进行匹配，形成一个带顺序、带标题、带文本内容、css结构的新数组arr）。



##### 前端css

```html
<div class="headerSroll" :class="{ 'headerSrollFlex': modelList.length > 10 }">
    <el-scrollbar>
        <div class="scrollbar-flex-content">
            <div v-for="(item, index) in modelList" :key="index" class="scrollbar-item">
                <div @click="handleDialogValue(item.modelTitle, item.id)">
                    {{ item.modelTitle }}
                </div>
            </div>
        </div>
    </el-scrollbar>
</div>
```

  这里根据modelList的长度进行判断，如果modelList长度大于10（即modelList中有超过10组数据），那么就添加一个headerSrollFlex属性。



将获取模板信息数据和拼接之前的数据分开

```js
const getModelList = async () => {

    if (store.getters.GET_NEWCATRGORYID != sessionStorage.getItem("newCategoryId")) {
        store.commit('RESET_NEWCATRGORYID', sessionStorage.getItem("newCategoryId"));
    }
    theCategoryId.value = store.getters.GET_NEWCATRGORYID;

    try {
        const response = await requestUtil.get("blogModel/list");
        modelList.value = response.data.modelList;
    } catch (error) {
        console.error(error)
    }
}

/**重新拼接(获取之前的博客内容) */
const getModelData = async () => {
    try {

        const result = await requestUtil.post("CategoryModel/list/" + theCategoryId.value);
        categoryModelList.value = result.data.categoryModelList;

        categoryModelList.value.forEach((item, index) => {
            listId.value++;
            const targetModel = modelList.value.find(model => model.id === item.modelId);
            if (targetModel) {
                arr.push({
                    arrNum: listId.value,
                    arrTitle: targetModel.modelTitle,
                    arrModelId: item.modelId,
                    arrCssL: targetModel.cssLeft,
                    arrPut: item.content,
                    arrCssR: targetModel.cssRight,
                });
            }
        });
    } catch (error) {
        console.error(error)
    }
}

onMounted(async () => {
    await getModelList();
    await getModelData();
});
```



#### 模板上传图片功能

目前思路：

表内模板信息仍然为p，但是模板添加的内容修改为一个div加上对应的img。

前端上传图片时，在dialog界面添加隐藏起来的input输入框，获取上传后的图片路径后，在input处将其补全。

##### 对模板信息进行过滤

将模板信息表中的上传图片功能独立出来，因为上传图片需要再设计一个dialog

```js
const response = await requestUtil.get("blogModel/list");
modelList.value = response.data.modelList.filter(item => item.id !== 9);
```

##### 设计上传图片使用的dialog



##### 遇到的问题

###### 问题一

由于将上面将上传图片的数据过滤掉了，导致无法进行拼接

解决方法：在获取模板信息数据时将上传图片的信息存储在另一个数组imgList中，当进行arr数组的拼接时，对halfArr的值进行判定，如果没有则从imgList中获取值（即获取上传图片的模板信息）

```js
imgList.value = response.data.modelList.filter(item => item.id == 9);		

halfArr.value = modelList.value.find((model) => model.id === modelId);
if (halfArr.value) {
getStr.value = inputStr;
} else {
halfArr.value = imgList.value.find((model) => model.id === modelId);
getStr.value = inputStr;
}
```

###### 问题二

在使用拼接的时候，由于部分特殊符号，导致输出的内容错误

解决方法（后被删除，详情可见下面问题三）:转义，将特殊字符串转换成对应的代码

```js
/**html格式特殊字符转义 */
function escapeHtml(str) {
    return str.replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/ /g, '&nbsp;')
        .replace(/'/g, '&#39;');
}
```

###### 问题三

在使用转义后，网页输出的是文本的内容

解决方法: 修改转换方法，添加title参数，将模板信息的标题传进来用于对方法进行判定，如果是上传图片，那么进行字符串的拼接，如果不是，则使用之前的方法。

```js
function highlight(title, str, left, right) {
    const arr = str.split(' ');
    let strHtml = '';
    if (title == '上传图片') {
        strHtml = '<' + left + '>' + str + '</' + right + '>';
    } else {
        arr.forEach(word => {
            strHtml += `<${left}>${word}</${right}>`;
        });
    }	
    return strHtml;
}
```

###### 问题四

将上传的图片保存之后，选择编辑，再次回到该界面，由于之前将模板信息数据将上传图片的数据进行了过滤，导致在获取之前存储的数据时，不显示图片（但是数据库中有值）。

解决方法：在初始化之前数组信息的时候，将数据进行合并(仅贴出修改和添加的部分)

```js
const completeModelList = reactive([])

completeModelList.value = [...modelList.value, ...imgList.value];

const targetModel = completeModelList.value.find(model => model.id === item.modelId);
```

###### 问题五

在上传图片后，左侧的组件栏中显示的是文本的内容，需要修改成图片

解决方法：对左侧组件栏的循环进行修改，通过对item.arrPut的内容进行判断，如果有图片则通过v-html将图片显示出来，如果没有，则显示文本。

循环处修改:（仅展示循环位置以及修改部分）

```vue
<div class="mainLFor" v-for="(item, arrNum) in  arr " :key="arrNum" draggable="true"
@dragstart="dragstart($event, arrNum)" @dragenter="dragenter($event, arrNum)"
@dragend="dragend" @dragover="dragover">
<div class="mainLForItem">

<div class="mainLForItemThree" v-if="containsImg(item.arrPut).hasImage"
v-html="containsImg(item.arrPut).value"></div>
<div class="mainLForItemThree" v-else>{{ containsImg(item.arrPut).value }}</div>

</div>
</div>
```

方法containsImg:

  首先对item.arrPut进行判断，如果没有img标签，则返回原来的值，如果有标签则对该字符串进行修改。

  let startIndex = str.indexOf('src="') + 5;：计算src属性的起始索引。src属性的起始索引是img标签中的src="子字符串的位置（索引）加5，因为src="的长度是5。

  let endIndex = str.indexOf('"', startIndex);：这一行计算src属性的结束索引。indexOf方法在这里用于查找双引号"在起始索引之后的位置。

  imagePath是将字符串中图片的路径获取到了，在进行拼接，因为需要添加一个新的css属性：object-fit: contain。

  object-fit: contain属性作用：保持纵横比缩放图片，使图片的长边能完全显示出来（即能够使图片完全显示出来）

```js
function containsImg(str) {
    if (str.indexOf('<img') !== -1) {
        // 存在图片
        let startIndex = str.indexOf('src="') + 5;
        let endIndex = str.indexOf('"', startIndex);
        const imagePath = str.substring(startIndex, endIndex);
        let newImgHtml = '<img style="height: 100%;width: 100%;object-fit: contain" src="' + imagePath + '" />';
        return { hasImage: true, value: newImgHtml }
    } else {
        // 不存在图片
        return { hasImage: false, value: str }
    }
}
```



为按钮设置css样式

Clip-Path属性

详情调整网站[[CSS Clip-Path Generator - CSS Portal](https://www.cssportal.com/css-clip-path-generator/)]



## 前后端调整

由于数据表中博客的类型以及权限没有对应的功能，因此在上面的基础上再进行细分 V 1.1

设计两个新的页面，以及数据库表（新页面用于展示部分博客，以及草稿箱内的博客）

### 展示权限内博客页面

#### 数据库

新建数据库表格

 id用于存储；userId用于存储查询的用户的id；categoryId用于存储博客id；sort用于存储用户指定的顺序

#### 后端

前端仅显示自己的博客和他人共享的博客,所以要返回对应的数据

mapper文件:

获取用户在博客页面显示的数据(符合要求的数据):

```xml
<select id="getCategoryList" resultType="cn.yaol.easyblog.entity.BlogCategory">
        SELECT * FROM blog_category WHERE (user_id =#{userid} AND category_type =1) OR (category_self =1 AND category_type =1) ORDER BY sort
    </select>
```

获取用户在博客界面存储的数据

```xml
<select id="getSortList" resultType="cn.yaol.easyblog.entity.BlogSort">
        SELECT * FROM blog_sort WHERE user_id = #{userid}
    </select>
```



#### 前端



创建一个方法，该方法使得非当前用户的博客无法被选中

```vue
<el-table-column type="selection" :selectable="canSelect" width="55" />

const canSelect = (row) => {
    if (row.userId == userid.value) {
        return true;
    } else {
        return false;
    }
}
```



修改页面展示数据



#### 修改（上一张/下一章）的方法

在博客详情页面的下方，有上一章和下一章，点击跳转查看其他的博客。

问题：当选择的是最顶端的博客或者最低端的博客时，对应上一章或者下一章应该没有内容，但存在到顶章后，下方的上一章显示的是该章的名称的情况，需要刷新后才会显现已经到顶部。

后端:

```java
@GetMapping("/categoryInfoPage/{categoryId}/userId/{userId}/managerType/{managerType}")
    public R categoryInfoPage(@PathVariable(value = "categoryId")Integer categoryId
            ,@PathVariable(value = "userId")Integer userid
            ,@PathVariable(value = "managerType")Integer managerType){
        Map<String,Object> resultMap = new HashMap<>();
        List<CategoryInfoPageVo> categoryInfoPageVoList = new ArrayList<>();
        List<BlogCategory> blogCategoryList;
        if(managerType == 1){
            blogCategoryList = blogSortService.categoryListGetSort(userid);
        }else {
            blogCategoryList = blogCategoryService.list();
        }

        for (BlogCategory blogCategory : blogCategoryList) {
            CategoryInfoPageVo categoryInfoPageVo = new CategoryInfoPageVo();
            categoryInfoPageVo.setCategoryId(blogCategory.getCategoryId());
            categoryInfoPageVo.setSort(blogCategory.getSort());
            categoryInfoPageVo.setCategoryName(blogCategory.getCategoryName());
            categoryInfoPageVoList.add(categoryInfoPageVo);
        }
        Collections.sort(categoryInfoPageVoList, (c1, c2) -> c1.getSort().compareTo(c2.getSort()));

        // 查找id所在的位置
        // 初始化索引为-1
        int index = -1;
        // 遍历categoryInfoPageVoList
        for (int i = 0; i < categoryInfoPageVoList.size(); i++) {
            // 如果当前元素的类别ID与传入的类别ID匹配
            if (categoryInfoPageVoList.get(i).getCategoryId().equals(categoryId)) {
                // 将索引设置为当前元素的索引，并跳出循环
                index = i;
                break;
            }
        }

        if (index >= 0) {
            // 返回id对应的前一组数据和后一组数据
            List<CategoryInfoPageVo> prevCategoryVoList = (index > 0) ? categoryInfoPageVoList.subList(index - 1, index) :  Arrays.asList(new CategoryInfoPageVo(0, -1, "已经到顶了"));
            List<CategoryInfoPageVo> nextCategoryVoList = (index < categoryInfoPageVoList.size() - 1) ? categoryInfoPageVoList.subList(index + 1, index + 2) : Arrays.asList(new CategoryInfoPageVo(0, -1, "已经到底部了"));
            resultMap.put("prevCategory", prevCategoryVoList);
            resultMap.put("nextCategory", nextCategoryVoList);
            return R.ok(resultMap);
        } else {
            return R.error();
        }

    }
```

这里通过managerType来对数据进行区别，如果managerType为1，那么只获得该用户能获取到的数组数据，否则获取所有的数组数据。

  如果index > 0（即当前元素不是第一个元素），那么prevCategoryVoList取前一个元素到当前元素（不包括当前元素）的子列表否则创建一个新的CategoryInfoPageVo类并返回。
  如果index < categoryInfoPageVoList.size() - 1（即当前元素不是最后一个元素），那么nextCategoryVoList取当前元素到后一个元素（不包括后一个元素）的子列表否则创建一个新的CategoryInfoPageVo类并返回。
  如果index等于0或categoryInfoPageVoList.size() - 1，那么分别返回一个包含特殊值的视图对象列表，分别表示已经到了顶部或底部。

```java
/**
     * @description: 将BlogSort的sort排序顺序赋值给BlogCategory
     * @author yaol
     * @date 2023/10/9 8:51
     * @version 1.0
     */
    @Override
    public List<BlogCategory> categoryListGetSort(Integer userid) {
        List<BlogCategory> categoryList = blogCategoryMapper.getCategoryList(userid);
        List<BlogSort> sortList = blogSortMapper.getSortList(userid);
        for (BlogCategory blogCategory : categoryList) {
            for (BlogSort blogSort : sortList) {
                if (blogCategory.getCategoryId().equals(blogSort.getCategoryId())) {
                    blogCategory.setSort(blogSort.getSort());
                    break;
                }
            }
        }
        return categoryList;
    }
```

前端:

```vue
<template>
<div class="mainFooter">
    <div class="footerL">
        <div v-if="prevCategorySort != -1" @click="TurnToPrevCategory">上一章:{{ prevCategoryName }}</div>
        <div v-else>已经到顶了</div>
    </div>
    <div class="footerR">
        <div v-if="nextCategorySort != -1" @click="TurnToNextCategory">下一章:{{ nextCategoryName }}</div>
        <div v-else>已经到底了</div>
    </div>
    </div>
    </div>
<el-button class="btn" type="danger" @click="TurnToManager()">
    返回
    </el-button>
</template>

<script setup>
import requestUtil from "@/util/request";
import { ref, onMounted, reactive } from 'vue';
import store from "@/store";
import { ElMessage } from "element-plus";
import router from '@/router';

const theCategoryId = ref(store.getters.GET_NEWCATRGORYID);
const managerType = ref(sessionStorage.getItem("managerType"));
const currentUser = ref(store.getters.GET_USERINFO);
const currentUserId = currentUser.value.userId;

/**获取其他博客的信息(上一章、下一章) */
const prevCategory = ref('')
const nextCategory = ref('')
const prevCategoryName = ref()
const nextCategoryName = ref()
const prevCategorySort = ref(0)
const nextCategorySort = ref(0)
const prevCategoryId = ref(0)
const nextCategoryId = ref(0)
const getPageCategoryData = async () => {

    // 获取所有模板信息
    const response = await requestUtil.get("Category/categoryInfoPage/" + theCategoryId.value + "/userId/" + currentUserId + "/managerType/" + managerType.value);
    if (response.data.code == 200) {

        prevCategory.value = response.data.prevCategory;
        nextCategory.value = response.data.nextCategory;
        prevCategory.value.forEach(item => {
            prevCategoryName.value = item.categoryName;
            prevCategorySort.value = item.sort;
            prevCategoryId.value = item.categoryId;
        })

        nextCategory.value.forEach(item => {
            nextCategoryName.value = item.categoryName;
            nextCategorySort.value = item.sort;
            nextCategoryId.value = item.categoryId;
        })

    } else {
        ElMessage.error(response.data.msg);
    }
}

onMounted(async () => {
    await getCurrentCategoryUser();
    await getCurrentCategoryData();
    await getPageCategoryData();
});
const TurnToPrevCategory = async () => {
    sessionStorage.removeItem("newCategoryId");
    store.commit('SET_NEWCATRGORYID', prevCategoryId.value);
    await getCurrentCategoryUser();
    infoList.splice(0);
    await getCurrentCategoryData();
    await getPageCategoryData();
}

const TurnToNextCategory = async () => {
    sessionStorage.removeItem("newCategoryId");
    store.commit('SET_NEWCATRGORYID', nextCategoryId.value);
    await getCurrentCategoryUser();
    // 清空数组
    infoList.splice(0);
    await getCurrentCategoryData();
    await getPageCategoryData();
}
</script>
```

由于后端当到顶或者到底的时候返回特殊的category，并且sort值都是-1，所以对sort的值进行判断，如果不为-1，那么则显示，如果为-1则显示已经到顶或者到底部。

#### 修改获取用户列表的方法

在测试使用用户增加博客后，发现用户博客界面没有更新，但是在管理所有博客界面能看到新增的博客。

选择对获取列表的方法进行更新

修改获取list的方法

```java
@GetMapping("/userCategoryList/{userid}")
    public R userCategoryList(@PathVariable(value = "userid")Integer userid){
        Map<String,Object> resutMap=new HashMap<>();
        List<BlogCategory> blogCategoryList = blogCategoryService.getCategoryList(userid);
        List<BlogSort> blogSortList = blogSortService.getSortList(userid);

        if(blogCategoryList.size() == blogSortList.size() + 1){
            // 用户新增了一个博客，但是sort表中没有更新。
            BlogCategory addBlogCategory = blogCategoryList.get(blogCategoryList.size()-1);
            blogSortService.addOneSort(addBlogCategory);
            blogSortList.clear();
            blogSortList = blogSortService.getSortList(userid);
        }else if(blogCategoryList.size() > blogSortList.size()){
            // 如果博客表中查询到的数据比blogSort标准中多
            // 删除BlogSort表中所有与userid有关的数据，再重新插入
            blogSortService.remove(new QueryWrapper<BlogSort>().in("user_id",userid));
            // 清空blogSortList
            blogSortList.clear();
            // 重新赋值
            blogSortList = blogSortService.getNewSortList(userid);
            blogSortService.saveBatch(blogSortList);
        }else {
            blogSortList.clear();
            // 重新赋值
            blogSortList = blogSortService.getSortList(userid);
        }

        List<BlogCategoryManagerVo> blogCategoryVoList = blogCategoryService.changeCategorySortList(blogSortList,blogCategoryList);
        resutMap.put("categoryVoList", blogCategoryVoList);

        return R.ok(resutMap);
    }
```

blogCategoryList.size() == blogSortList.size() + 1 进行判断，当用户新增博客后，就会出现这种情况，此时获取数组中最后一位BlogCategory（根据排序方式可以知道，这是用户新增的），将该实体类传递到addOneSortf方法中。

在BlogSortServiceImpl中添加一个方法(该方法是将一个新的BlogSort实体类插入表中)

```java
public void addOneSort(BlogCategory blogCategory) {
        BlogSort newBlogSort = new BlogSort();
        int theSort = blogSortMapper.getSortList(blogCategory.getUserId()).size();
        newBlogSort.setCategoryId(blogCategory.getCategoryId());
        newBlogSort.setUserId(blogCategory.getUserId());
        newBlogSort.setSort(theSort);
        blogSortMapper.insert(newBlogSort);
    }
```

#### 修改删除方法

在对博客进行删除或者批量删除后，需要对表中的博客重新排序

（需要在所有博客数据表blogCategory和用户界面展示表blogSort中写）

mapper

```xml
<select id="getSortList" resultType="cn.yaol.easyblog.entity.BlogCategory">
        SELECT * FROM blog_category ORDER BY sort
</select>
```

在BlogCategoryServiceImpl中添加方法reSortCategory (将blogCategoryService注入到BlogCategoryServiceImpl之后，能够使用mybatis-plus提供的saveOrUpdateBatch方法)

```java
    @Autowired
    private IService<BlogCategory> blogCategoryService;

    /*
     * @description: 在删除某些博客后对博客数组重新排序
     * @Date 22:44 2023/10/29
     * @author yaol
     */
    @Override
    public void reSortCategory() {
        List<BlogCategory> blogCategoryList = blogCategoryMapper.getSortList();
        // 遍历排序后的数组，将索引值赋给每个元素的sort属性
        for (int i = 0; i < blogCategoryList.size(); i++) {
            blogCategoryList.get(i).setSort(i);
        }
        blogCategoryService.saveOrUpdateBatch(blogCategoryList);
    }
```

在BlogSortServiceImpl中实现reChangeSort方法：

由于BlogSort表中博客id，博客sort会重复，因此不能像BlogCategory那样重新排序。

选择通过userid进行分组，每组进行重新排序

mapper

```xml
<select id="getSortUserIdList" resultType="java.lang.Integer">
        SELECT DISTINCT user_id FROM blog_sort
</select>
```

BlogSortServiceImpl

```java
    @Autowired
    private IService<BlogSort> blogSortService;

    /*
     * @description: (在删除博客后调用)对sort表中的数据重新排序，
     * 首先获取该表中所有的用户id(有些用户id在该表中没有数据，因此不从user表中获取userid)
     * 根据id获取到对应的博客数组，对每个数组重新排序
     * @Date 10:27 2023/10/30
     * @param:
     * @return:
     * @author yaol
     */
    @Override
    public void reChangeSort() {
        List<Integer> userIdList = blogSortMapper.getSortUserIdList();
        // 对sort表中的用户id进行遍历
        for (Integer userId : userIdList){
            List<BlogSort> currentSortList = blogSortMapper.getSortList(userId);
            // 根据博客的sort排序
            Collections.sort(currentSortList, (c1, c2) -> c1.getSort().compareTo(c2.getSort()));
            // 生成新的sort值(即使得sort值是连续的)
            for (int i = 0; i < currentSortList.size(); i++) {
                currentSortList.get(i).setSort(i);
            }
            blogSortService.saveOrUpdateBatch(currentSortList);
        }
    }
```

修改后的controller(管理页面和用户页面公用这个接口)

```java
@Transactional
@PostMapping("/delete")
public R delete(@RequestBody Integer[] ids){
    if (ids != null && ids.length > 0) {
        blogCategoryService.removeByIds(Arrays.asList(ids));
        // 删除sort表中的数据
        blogSortService.removeByIds(Arrays.asList(ids));
        // 重新排序
        blogSortService.reChangeSort();
        // 更新博客排序
        blogCategoryService.reSortCategory();
        // 将存储博客内容的表中数据删除
        categoryModelService.remove(new QueryWrapper<CategoryModel>().in("category_id",ids));
        return R.ok();
    }else {
        return R.error("删除出现错误");
    }
}
```



### 个人中心页面

#### 实现下雨特效

原文参考[[在vue中使用canvas实现简单特效（下雨天）_博客全屏下雨特效怎么弄-CSDN博客](https://blog.csdn.net/weixin_44037364/article/details/121952035)]

vue页面

```vue
<template>
  <div class="home">
    <canvas class="canvasStyle" id="canvas"></canvas>
  </div>
</template>
  
  <script setup>
import { init } from "@/util/rain.js";
import { ref, onMounted } from 'vue'

const ctx = ref(null)

function initCanvas() {
  const canvas = document.querySelector('#canvas')
  ctx.value = canvas.getContext('2d')
  canvas.width = window.innerWidth
  canvas.height = window.innerHeight
  init(ctx.value)
}

onMounted(() => {
  initCanvas()
})
  </script>
<style>
.home {
  height: 100vh;
  width: 100%;
  position: relative;
}

.canvasStyle {
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: -1;
  left: 0;
  bottom: 0;
}
</style>
```

js文件

```js
// 画笔
var ctx;
// 画布的宽高
var w = window.innerWidth;
var h = window.innerWidth;
// 存放雨滴的数组
var arr = [];
// 雨滴的数量
var size = 150;
// 雨滴的构造函数
class Rain {
    x = random(w);
    y = random(h);
    ySpeed = random(2) + 5;
    show() {
        drawLine(this.x, this.y);
    }
    run() {
        if (this.y > h) {
            this.y = 0;
            this.x = random(w);
        }
        this.y = this.y + this.ySpeed;
    }
}
// 画线（雨滴）
function drawLine(x1, y1) {
    ctx.beginPath();
    ctx.strokeStyle = "#cccccc";
    ctx.moveTo(x1, y1);
    // 雨长度为30
    ctx.lineTo(x1, y1 + 30);
    ctx.stroke();
}
// 生成随机数
function random(num) {
    return Math.random() * num;
}
// 开始
function start() {
    for (var i = 0; i < size; i++) {
        var rain = new Rain();
        arr.push(rain);
        rain.show();
    }
    setInterval(() => {
        ctx.clearRect(0, 0, w, h);
        for (var i = 0; i < size; i++) {
            arr[i].show();
            arr[i].run();
        }
    }, 10);
}
// 初始化
function init(ctx1) {
    ctx = ctx1;
    start();
}
// 导出初始化函数
export { init };
```

#### 实现上传更换头像

##### 功能实现部分

vue页面

```vue
<el-card class="box-card" style="width: 250px">
    <template #header>
<div class="card-header sceneLeftMainHeader">
    <span>修改信息</span>
        </div>
    </template>
    <div class="text item sceneLeftMainItem">
        <el-form ref="avatarUpload" class="el-form elAvatarForm" :model="avatarForm" label-width="100px">
            <div class="elAvatarFormTop">点击更换头像</div>
            <div class="elAvatarFormMain">
                <el-upload class="avatar-uploader elAvatarFormMainImg" :action="getServerUrl() + 'user/uploadAvatar'" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                    <img v-if="avatarForm.avatar" :src="imageUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Icon :iconHref="'#icon-tianjia2'" />
                    </el-icon>
                </el-upload>
            </div>
            <el-button class="elAvatarFormFooter" @click="handleConfirm">确认更换</el-button>
        </el-form>
        <el-input v-model="avatarForm.avatar" type="hidden"></el-input>
    </div>
</el-card>
<script setup>
const imageUrl = ref("");
const avatarUpload = ref(null);
const avatarForm = ref({
  userId: -1,
  avatar: "",
});

const handleAvatarSuccess = (res) => {
  imageUrl.value = getServerUrl() + res.data.src;
  avatarForm.value.avatar = res.data.title;
};

const beforeAvatarUpload = (file) => {
  const isJPG = file.type === "image/jpeg";
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isJPG) {
    ElMessage.error("图片必须是jpg格式");
  }
  if (!isLt2M) {
    ElMessage.error("图片大小不能超过2M!");
  }
  return isJPG && isLt2M;
};

const handleConfirm = async () => {
  avatarForm.value.userId = userid;
  console.log("avatarForm:" + JSON.stringify(avatarForm.value))
  let result = await requestUtil.post("user/updateAvatar", avatarForm.value);
  let data = result.data;
  if (data.code == 200) {
    const currentUser = data.currentUser;
    ElMessage.success("执行成功！")
    store.commit("SET_USERINFO", currentUser);
  } else {
    ElMessage.error(data.msg);
  }
};
</script>
```

el-upload的:action 用于向后台提交文件

beforeAvatarUpload用于检测图片的大小格式等是否符合规范

handleAvatarSuccess当上传成功后进行回显(此时只是将上传的图片显示，并没有更改)

handleConfirm 提交数据，更改用户头像等

后台

```java
@RequestMapping("/uploadAvatar")
public Map<String,Object> uploadCover(MultipartFile file)throws Exception{
    Map<String,Object> resultMap=new HashMap<>();
    if(!file.isEmpty()){// 获取文件名
        String originalFilename = file.getOriginalFilename();
        String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName= "avatar_"+ DateUtil.getCurrentDateStr()+suffixName;
        FileUtils.copyInputStreamToFile(file.getInputStream(),new File(avatarImagesFilePath+newFileName));
        resultMap.put("code",0);
        resultMap.put("msg","上传成功");
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("title",newFileName);
        dataMap.put("src","images/avatar/"+newFileName);
        resultMap.put("data",dataMap);
    }
    return resultMap;
}
```

将提交的文件重新命名并复制到指定的位置，同时将文件名字和路径返回

```java
@RequestMapping("/updateAvatar")
public R updateAvatar(@RequestBody User user){
    User currentUser = userService.getById(user.getUserId());
    currentUser.setLastTime(new Date());
    currentUser.setAvatar(user.getAvatar());
    userService.updateById(currentUser);
    return R.ok().put("currentUser",currentUser);
}
```

更新用户的头像属性,同时返回当前用户，用于显示新的头像

##### 功能优化

此时更新完头像后，并不能立马显示，需要重新登录或者刷新页面，需要进行优化

上方的头像布局中添加一个watch对session进行监听（个人信息页面代码类似，放到下面一起）

```js
watch(
  () => store.getters.GET_USERINFO,
  (newUserInfo, oldUserInfo) => {
    currentUser.value = newUserInfo;
    squareUrl.value = getServerUrl() + 'images/avatar/' + currentUser.value.avatar;
  }
);
```

#### 实现用户修改昵称

##### 实现功能部分

前端页面

```vue
<el-card class="box-card" style="width: 400px;background: none;">
    <template #header>
<div class="card-header sceneLeftMainHeader">
    <span>修改昵称</span>
        </div>
    </template>
    <div v-if="changeNameFlag == 0" class="text item sceneLeftMainItem">
        <el-button class="sceneLeftMainItemRightBtn" @click="showChangeUserTrueNameInput">点击修改昵称</el-button>
    </div>
    <div v-else class="text item sceneLeftMainItem">
        <el-input v-model="inputtruename" type="text" size="large" auto-complete="off" placeholder="新的昵称">
            <template #prefix>
<el-icon class="iconfont icon-denglu" />
            </template>
        </el-input>
        <el-button class="sceneLeftMainItemRightBtn" @click="confirmChangeUserTrueName">保存</el-button>
    </div>
</el-card>
<script setup>
/**修改昵称 */
let changeNameFlag = ref(0);

const showChangeUserTrueNameInput = () => {
  changeNameFlag.value++;
}

const confirmChangeUserTrueName = async () => {
  let result = await requestUtil.post("user/checkTrueName?tname=" + inputtruename.value);
  let data = result.data;
  if (data.code == 200) {
    let res = await requestUtil.post("user/changeTrueName?id=" + userid.value + "&tname=" + inputtruename.value);
    let data = res.data;
    if (data.code == 200) {
      const currentUser = data.currentUser;
      ElMessage.success("修改昵称成功")
      inputtruename.value = "";
      store.commit("SET_USERINFO", currentUser);
    } else {
      inputtruename.value = "";
      ElMessage.error("修改昵称存在异常，请联系工作人员");
    }
  } else {
    ElMessage.error("该昵称已存在");
  }
  changeNameFlag.value--;
}
</script>
```

通过changeNameFlag属性来判断显示修改昵称的输入框

confirmChangeUserTrueName方法中先对昵称查找，如果已经存在则返回错误，如果没有则进行下一步，进行更新，更新成功后将数据修改到session中

后端

```java
@PostMapping("/checkTrueName")
public R checkTrueName(@RequestParam("tname") String truename){
    if(userService.getByTruename(truename)==null){
        return R.ok();
    }else{
        return R.error();
    }
}

@PostMapping("/changeTrueName")
public R changeTrueName(@RequestParam("id") String userid,@RequestParam("tname") String truename){
    User currentUser = userService.getById(userid);
    currentUser.setLastTime(new Date());
    currentUser.setTname(truename);
    boolean flag = userService.updateById(currentUser);
    if(flag){
        return R.ok().put("currentUser",currentUser);
    }else {
        return R.error();
    }
}
```

##### 功能优化

页面中使用v-for将名称循环进行输出,当使用之前的监听时，charArray数据修改后但是页面不能及时渲染

```vue
<span v-for="(char, index) in charArray" :key="index" :style="`--i: ${index}`">{{char}}</span>
```

添加代码对session进行监听

```js
watch(
  () => store.getters.GET_USERINFO,
  (newUserInfo, oldUserInfo) => {
    currentUser.value = newUserInfo;
    while (charArray.length) {
      charArray.pop();
    }
    let truename = ref(currentUser.value.tname);
    for (let i = 0; i < truename.value.length; i++) {
      charArray.push(truename.value.charAt(i));
      console.log("charArray:" + truename.value.charAt(i));
    }
    squareUrl.value = getServerUrl() + 'images/avatar/' + currentUser.value.avatar;
  }
);
```

通过查询[[vue中v-for绑定数组，当数组变化时页面数据不更新（已解决）_vue v-for绑定数组-CSDN博客](https://blog.csdn.net/qq_42586895/article/details/104080463)]此网页提到需要使用push()、pop()、shift()、unshift()、splice()、sort()、reverse()其中的方法，才能使得视图及时更新，因此使用pop()的方法，将数组清空，然后重新添加数据

#### 实现用户修改密码

前端页面 将用户的id，原密码，和新密码传递到后端

```vue
<template>
<el-card class="box-card" style="width: 250px;background: none;">
    <template #header>
        <div class="card-header sceneLeftMainHeader">
            <span>修改密码</span>
    </div>
</template>
<div v-if="changePasswordFlag == 0" class="text item sceneLeftMainItem">
    <el-button class="sceneLeftMainItemRightBtn" @click="showChangeUserPasswordInput">点击修改密码</el-button>
</div>
<div v-else class="text item sceneLeftMainItem" style="flex-direction: column;">
    <el-input v-model="inputOldPassword" type="text" size="large" auto-complete="off" placeholder="旧密码">
        <template #prefix>
<el-icon class="iconfont icon-denglu" />
        </template>
    </el-input>
    <el-input v-model="inputNewPassword" type="password" size="large" auto-complete="off" placeholder="新密码">
        <template #prefix>
<el-icon class="iconfont icon-denglu" />
        </template>
    </el-input>
    <el-input v-model="inputConfirmPassword" type="password" size="large" auto-complete="off" placeholder="再次输入新密码">
        <template #prefix>
<el-icon class="iconfont icon-denglu" />
        </template>
    </el-input>
    <el-button class="sceneLeftMainItemRightBtn" @click="confirmChangeUserPassword">保存</el-button>
</div>
</el-card>
</el-space>
</template>
<script setup>
let userid = ref(currentUser.value.userId);

let inputOldPassword = ref("");
let inputNewPassword = ref("");
let inputConfirmPassword = ref("");
let changePasswordFlag = ref(0);

const showChangeUserPasswordInput = () => {
  changePasswordFlag.value++;
}

const confirmChangeUserPassword = async () => {
  if (inputOldPassword.value == "") {
    ElMessage.error("请输入原密码！");
    return false;
  }
  if (inputNewPassword.value == "") {
    ElMessage.error("请输入新密码！");
    return false;
  }
  if (inputNewPassword.value != inputConfirmPassword.value) {
    ElMessage.error("两次新密码不一致！！！");
    return false;
  }
  let result = await requestUtil.post("user/changePassword?id=" + userid.value + "&password=" + inputOldPassword.value + "&newPassword=" + inputNewPassword.value);
  let data = result.data;
  if (data.code == 200) {
    const currentUser = data.currentUser;
    ElMessage.success("修改密码成功")
    inputOldPassword.value = "";
    inputNewPassword.value = "";
    inputConfirmPassword.value = "";
    store.commit("SET_USERINFO", currentUser);
    changePasswordFlag.value--;
  } else {
    ElMessage.error(data.msg);
  }
}
</script>
```

后端

service层

```java
@Override
public User getByIdAndPassword(Integer userid, String password) {
User currentUser = userMapper.selectById(userid);
String currentSalt = currentUser.getSalt(); // 获取当前用户的盐值
String md5Password = getMd5Password(password, currentSalt); // 将用户输入的密码和盐值一起进行加密
User haveUser = userMapper.getUserByIdAndPasswrod(userid,md5Password);//根据id与密码查询是否有该用户
return haveUser;
}
```

controller层

```java
    @PostMapping("/changePassword")
    public R changePassword(@RequestParam("id") Integer userid,@RequestParam("password") String password,@RequestParam("newPassword") String newPassword){
        User currentUser = userService.getByIdAndPassword(userid,password);
        if(currentUser == null){
            return R.error("原密码错误!");
        }
        String salt = currentUser.getSalt();
        String newPsw = userService.getFinalPassword(newPassword,salt);
        currentUser.setPassword(newPsw);
        currentUser.setLastTime(new Date());
        boolean flag = userService.updateById(currentUser);
        if(flag){
            return R.ok().put("currentUser",currentUser);
        }else {
            return R.error("更新密码产生错误!");
        }
    }
```

首先通过id和密码对用户进行查询，如果没有就是密码出错了，然后将新密码进行加密，赋值给当前的user类，再更新这个user的数据。

### 展示草稿箱博客页面

与之前的相类似，仅获取博客表中的草稿的信息，仅写出不同的地方

#### 获取草稿列表

```js
jsconst getUserCategoryList = async () => {
  userid.value = currentUser.value.userId;
  while (categoryVoList.value.length > 0) {
    categoryVoList.value.pop();
  }
  let result = await requestUtil.get("Category/userDraftCategoryList/" + userid.value);
  if (result.data.code == 200) {
    categoryVoList.value = result.data.categoryList;
  }
};
```

后端

controller

```java
@GetMapping("/userDraftCategoryList/{userid}")
public R userDraftCategoryList(@PathVariable(value = "userid")Integer userid){
    Map<String,Object> resutMap=new HashMap<>();
    List<BlogCategory> blogCategoryList = blogCategoryService.getCategoryDraftList(userid);
    resutMap.put("categoryList", blogCategoryList);
    return R.ok(resutMap);
}
```

mapper

```xml
<select id="getCategoryDraftList" resultType="cn.yaol.easyblog.entity.BlogCategory">
        SELECT * FROM blog_category WHERE user_id =#{userid} AND category_type =0 ORDER BY sort
</select>
```

#### 将草稿修改为正文

前端

```js
const changeTypeId = async (id) => {
  let res = await requestUtil.get("Category/changeCategoryType/" + id);
  if (res.data.code == 200) {
    ElMessage.success("执行成功!");
  } else {
    ElMessage.error("修改博客分类出现错误，请联系工作人员");
  }
  getUserCategoryList();
};
```

后端

```java
@GetMapping("/changeCategoryType/{categoryId}")
public R changeCategoryType(@PathVariable(value = "categoryId")Integer categoryId){
    BlogCategory currentBlogCategory = blogCategoryService.getById(categoryId);
    currentBlogCategory.setCategoryType("1");
    blogCategoryService.saveOrUpdate(currentBlogCategory);
    return R.ok();
}
```

其他接口等皆可复用之前的



## 模块

安装了fastapi

测试了python的接口
