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

    /**
     * 递归方法树形结构
     * 父ID属性为parent
     */
    public static array2Tree(array:any,parentId:number){
        if (Tool.isEmpty(array)){
            return [];
        }
        const result=[];
        for (let i = 0; i < array.length; i++) {
            const c=array[i];
            if (Number(c.parent)==Number(parentId)){
                result.push(c);
            //    递归查看当前节点对应的子节点
                const children=Tool.array2Tree(array,c.id);
                if (Tool.isNotEmpty(children)){
                    c.children=children;
                }
            }
        }
        return result;
    }

    /**
     * 随机生成[len]长度的[radix]进制数
     *
     */
    public static uuid(len :number,radix=62){
        const chars='0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        const uuid=[];
        radix=radix||chars.length;
        for (let i = 0; i < len; i++) {
            uuid[i]=chars[0|Math.random()*radix];
        }
        return uuid.join('');
    }
}