<template>
        <el-icon>
            <svg class="icon" aria-hidden="true">
                <use :xlink:href="iconHref"></use>
            </svg>
        </el-icon>
</template>
<script setup>
import { defineProps } from "vue";

const props = defineProps(
    {
        iconHref: {
            type: String,
            required: true
        }
    }
)
</script>
<style scoped>
.icon {
    width: 1em;
    height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
}
</style>