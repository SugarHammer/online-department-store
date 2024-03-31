<template>
    <div>
        <el-menu
        :default-active="$route.path"
        mode="horizontal"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        router>
<!--            <el-menu-item v-for="(item, i) in lists" :key="i" :index="item.index" :class="item.class">-->
<!--                <i :class="item.i_class"></i>{{ item.des }}-->
<!--            </el-menu-item>-->


          <el-menu-item index="/"><i class="el-icon-shopping-bag-1"></i><span v-text="$t('m.webshop')"> </span></el-menu-item>
            <el-menu-item :index="menuItem.index" class="item"><i class="el-icon-s-check"></i><span v-text="$t('m.mine')"/></el-menu-item>
            <!-- <el-menu-item index="/collect" class="item"><i class="el-icon-star-on"></i><span v-text="$t('m.favorites')"> </span></el-menu-item> -->
            <el-menu-item index="/order" class="item"><i class="el-icon-s-order"></i><span v-text="$t('m.order')"> </span></el-menu-item>
            <el-menu-item index="/shopCart" class="item"><i class="el-icon-shopping-cart-2"></i> <span v-text="$t('m.cart')"/> </el-menu-item>
            <el-menu-item  class="item" @click="changeLangUS">English</el-menu-item>
            <el-menu-item  class="item" @click="changeLangCN">中文</el-menu-item>
        </el-menu>
    </div>
</template>

<script>
export default {
    data() {
        return {
            activeIndex: '/',

            menuItem: {
                index: '',
                name: ''
            },

        }
    },
    props:{
        isAdmin: Boolean
    },
    computed:{
        // 页面头部渲染列表
        lists: function() {
            if(!this.isAdmin) {
                // 用户页面
                return [
                    {
                        index: '/',
                        class: '',
                        i_class: 'el-icon-shopping-bag-1',
                        des: '在线百货购物商城'
                    },
                    {
                        index: this.menuItem.index,
                        class: 'item',
                        i_class: 'el-icon-s-check',
                        des: this.menuItem.name
                    },
                    // {
                    //     index: '/collect',
                    //     class: 'item',
                    //     i_class: 'el-icon-star-on',
                    //     des: '收藏夹'
                    // },
                    {
                        index: '/order',
                        class: 'item',
                        i_class: 'el-icon-s-order',
                        des: '我的订单'
                    },
                    {
                        index: '/shopCart',
                        class: 'item',
                        i_class: 'el-icon-shopping-cart-2',
                        des: '购物车'
                    }
                ];
            }else{
                // 管理员页面
                return [
                    {
                        index: '/admin',
                        class: '',
                        i_class: 'el-icon-shopping-bag-1',
                        des: '在线百货购物商城-上架商品'
                    },
                    {
                        index: '/',
                        class: 'item',
                        i_class: 'el-icon-error',
                        des: '退出'
                    },
                    {
                        index: '/adminOrder',
                        class: 'item',
                        i_class: 'el-icon-s-order',
                        des: '订单'
                    },
                ]
            }
        }
    },
    methods: {
      //国际化
      changeLangCN(){

        this.$i18n.locale = 'zh-CN';//切换中文

      },
      changeLangUS(){

        this.$i18n.locale = 'en-US';//切换英文

      }
    },
    created() {
        if(!localStorage.getItem('user')) {
            this.menuItem.index = '/login';
            this.menuItem.name = '登陆 / 注册';
        } else {
            this.menuItem.index = '/user';
            this.menuItem.name = '个人中心';
        }
    },
};
</script>

<style scoped>
.item {
    float: right !important;
}
</style>

