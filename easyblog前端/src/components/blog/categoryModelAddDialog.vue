<template>
    <el-dialog :modelValue="modelDialogVisible" title="添加新的css模板" width="40%" @close="handleClose" center>
        <el-form ref="formRef" :model="form" :rules="rules" label-width="220px">
            <el-form-item label="模板的名称" prop="modelTitle">
                <el-input v-model="form.modelTitle" />
            </el-form-item>
            <el-form-item label="左边的css结构'<>'中的内容" prop="cssLeft">
                <el-input v-model="form.cssLeft" />
            </el-form-item>
            <el-form-item label="右边的css结构'</>'中的内容" prop="cssRight">
                <el-input v-model="form.cssRight" />
            </el-form-item>
            <el-form-item label="权限" prop="type">
                <el-radio-group v-model="form.type">
                    <el-radio :label="'0'">仅自己可用</el-radio>
                    <el-radio :label="'1'">共享</el-radio>
                </el-radio-group>
            </el-form-item>
        </el-form>
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
import requestUtil from "@/util/request";
import { ElMessage } from "element-plus";
import store from "@/store";
const props = defineProps(
    {
        modelDialogVisible: {
            type: Boolean,
            default: false,
            required: true
        }
    }
)

const currentUser = ref(store.getters.GET_USERINFO);

const form = ref({
    modelTitle: "",
    cssLeft: "",
    cssRight: "",
    type: "1",
    createUserid: currentUser.value.userId
})
const formRef = ref(null)
const checkTitle = async (rule, value, callback) => {
    const res = await requestUtil.post("blogModel/checkTitle",
        { modelTitle: form.value.modelTitle });
    if (res.data.code == 500) {
        callback(new Error("标题已存在！"));
    } else {
        ElMessage.success('标题可使用');
        callback();
    }
}

const rules = ref({
    modelTitle: [
        { required: true, message: '请输入模板名称' },
        { required: true, validator: checkTitle, trigger: "blur" }
    ],
    cssLeft: [{ required: true, message: "左边css结构不能为空", trigger: "blur" }],
    cssRight: [{ required: true, message: "右边css结构不能为空", trigger: "blur" }],
})

const emits = defineEmits(['update:modelValue', 'getModelList'])
const handleClose = () => {
    emits('update:modelValue', false)
}

const handleConfirm = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let result = await requestUtil.post("blogModel/saveModel", form.value);
            let data = result.data;
            if (data.code == 200) {
                ElMessage.success("执行成功！")
                formRef.value.resetFields();
                emits('getModelList')
                handleClose();
            } else {
                ElMessage.error(data.msg);
            }
        } else {
            console.log("没有通过表单验证")
        }
    })
}
</script>
<style ></style>