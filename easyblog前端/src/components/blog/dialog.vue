<template>
  <el-dialog :modelValue="dialogVisible" :title="dialogTitle" width="40%" @close="handleClose" center>
    <el-row :gutter="20" class="row">
      <span class="rowTitle">请输入对应文本内容：</span>
      <el-input class="rowInput" v-model="inputStr" />
    </el-row>
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

const inputStr = ref('')

const emits = defineEmits(['update:modelValue', 'inputStr'])
const handleClose = () => {
  emits('update:modelValue', false)
  inputStr.value = '';
}

const handleConfirm = () => {
  // 检查 inputStr 是否为空
  if (!inputStr.value) {
    ElMessage.warning('请输入内容');
    return;
  }

  emits('inputStr', { inputStr: inputStr.value, title: props.dialogTitle, modelId: props.dialogModelId });
  handleClose();
}
</script>
<style >
.row {
  display: flex;
  height: 100%;
  width: 100%;
  align-items: center;
  justify-content: center;
  flex-direction: row;
}

.rowTitle {
  flex: 1;
}

.rowInput {
  flex: 3;
}
</style>