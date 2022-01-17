//使用于编辑时不许要动态绑定数据
export class Tool{


    /**
     * 空校验null或""都返回true
     * 可以使空串、数组、对象
     */
    public static isEmpty(obj:any){
        if ((typeof obj==='string')){
            return !obj||obj.replace(/\s+/g,"") === ""
        }else {
            return (!obj||JSON.stringify(obj)==="{}"||obj.length===0);
        }
    }

    /**
     * 判断一个对象是否为空
     *非空校验
     */
    public static isNotEmpty(obj:any){
         return ! this.isEmpty(obj);
    }

    /**
     * 对象复制
     * @param obj
     */
    public static copy(obj:any){
        if (Tool.isNotEmpty(obj)){
            // 转化为json对象
            return JSON.parse(JSON.stringify(obj));
        }
    }
}