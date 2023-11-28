<template>
    <el-dropdown>
        <span class="el-dropdown-link bigIcon" draggable="true">
            <Icon id="icon" :iconHref="isPlayed ? '#icon-zhanting' : '#icon-jixu'" />
        </span>
        <template #dropdown>
            <el-dropdown-menu style="background-color: black;">
                <input type="range" id="musicChange" name="musicChange" min="0" max="100" v-model="volumeValue" step="1" />
                <span id="rangeValue">{{ volumeValue }}</span>
            </el-dropdown-menu>
        </template>
    </el-dropdown>
    <audio id="bgm" loop :volume="volumeValue / 100">
        <source src="@/assets/fight.mp3" type="audio/mp3">
    </audio>
</template>
<script setup>
import Icon from './Icon.vue';
import { ref, onMounted, computed } from 'vue';
import store from '@/store';

const volumeValue = ref(80)

const volume = ref()

const isPlayed = computed(() => store.state.isPlayed);

onMounted(() => {
    var bgm = document.getElementById("bgm")
    var icon = document.getElementById("icon")

    volume.value = bgm.volume / 100

    icon.onclick = function () {
        if (bgm.paused) {
            bgm.play();
            store.commit('playChange');
        } else {
            bgm.pause();
            store.commit('playChange');
        }
    }
})
</script>
<style >
#musicChange {
    width: 80px;
    height: 10px;
    background: #111;
    appearance: none;
    border: 2px solid white;
    border-radius: 10px;
    outline: none;
    box-shadow: 0 0 5px #e8f717, 0 0 10px #e8f717,
        inset 0 0 5px #e8f717, inset 0 0 10px #e8f717;
    overflow: hidden;
}

#musicChange::-webkit-slider-thumb {
    appearance: none;
    width: 10px;
    height: 10px;
    background: white;
    border-radius: 10px;
    border: 2px solid #ef0d0d;
    box-shadow: calc(-40px - 3px) 0 0 40px rgba(253, 157, 54, 0.832),
        inset 0 0 5px #ef0d0d;
    cursor: pointer;
}

#rangeValue {
    position: relative;
    box-sizing: border-box;
    text-align: center;
    display: block;
    font-size: 2em;
    color: rgb(166, 166, 166);
    font-weight: 100;
    z-index: 1;
}

#rangeValue::after {
    content: "%";
}

.bigIcon {
    font-size: 25px;
}
</style>