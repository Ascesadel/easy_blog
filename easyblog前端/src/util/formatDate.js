import { ta } from "element-plus/es/locale";

//方法一
export function formatDate(val) {
    var date = new Date(Number(val)); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + "-";
    var M = (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1) + "-";
    var D = date.getDate() + " ";
    var h = date.getHours() + ":";
    var m = date.getMinutes() + ":";
    var s = (date.getSeconds() < 10 ? "0" + (date.getSeconds()) : date.getSeconds());
    return Y + M + D + h + m + s;
}

// var minSubArrayLen = function (target, nums) {
//     let left = 0;
//     let right = 0;
//     let result = 0;
//     let bestResult = 0;

//     while (right < nums.length) {
//         result = result + nums[right];
//         while (result >= target) {

//             if (right - left + 1 < bestResult || bestResult == 0) {
//                 bestResult = right - left + 1;
//             }
//             result = result - nums[left];
//             left++;
//         }
//         right++;
//     }
//     return bestResult;
// };