<template>
    <el-dialog :modelValue="dialogImgVisible" title="添加图片内容" width="30%" @close="handleClose" center>
        <el-upload class="content-uploader" :action="getServerUrl() + 'blogModel/uploadContent'" :show-file-list="false"
            :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="content" />
            <el-icon v-else class="contentIcon">
                <Icon :iconHref="'#icon-tianjia'" />
            </el-icon>
        </el-upload>
        <el-input v-model="input" style="display: none;" />
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="handleConfirm">确认</el-button>
                <el-button @click="handleClose">取消</el-button>
            </span>
        </template>
    </el-dialog>
</template>
<script setup>
import { defineEmits, defineProps, ref } from "vue";
import requestUtil, { getServerUrl } from "@/util/request";
import Icon from '@/components/Icon.vue';
import { ElMessage } from "element-plus";
const props = defineProps(
    {
        dialogImgVisible: {
            type: Boolean,
            default: false,
            required: true
        }
    }
)

/**上传封面类 */
const imageUrl = ref("")
const input = ref('')

const handleAvatarSuccess = (res) => {
    imageUrl.value = getServerUrl() + res.data.src;
    /** 将图片信息和html信息拼接 */
    // input.value = '<div style="width: 200px;height: 200px;margin: 0 auto;"><img style="height: 100%;width: 100%;" src="' + imageUrl.value + '"/></div>'
    input.value = '<img style="height: 100%;width: 100%;" src="' + imageUrl.value + '"';
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

const inputStr = ref('')

const emits = defineEmits(['update:modelValue', 'inputStr'])
const handleClose = () => {
    emits('update:modelValue', false)
    inputStr.value = '';
}

/** 提交 */
const handleConfirm = () => {
    // 检查 inputStr 是否为空
    if (!imageUrl.value) {
        ElMessage.warning('请添加图片');
        return;
    }
    console.log("提交时的input:" + input.value)
    ElMessage.success("图片上传成功！");
    emits('inputStr', { inputStr: input.value, title: '上传图片', modelId: 9 });
    handleClose();
}
</script>
<style >
.content-uploader {
    width: 60%;
    height: 80%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.content-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.el-dialog__body {
    display: flex;
    align-items: center;
    justify-content: center;
}

.content-uploader .el-upload:hover {
    border-color: #409eff;
}

.el-icon.content-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    font-size: 150px;
    text-align: center;
}

.content-uploader .content {
    width: 160px;
    height: 160px;
    display: block;
}

.content-uploader .contentIcon {
    width: 160px;
    height: 160px;
    font-size: 160px;
    display: block;
}
</style>