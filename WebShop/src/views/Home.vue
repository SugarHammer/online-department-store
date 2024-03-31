<template>
    <div>
        <!-- 搜索框 -->
        <div>
            <el-form :inline="true" :model="form" class="form">
                <el-form-item>
                    <el-input v-model="form.query" placeholder="请输入要搜索的词" class="input" id="serch_good"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSearch">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>

        <!-- 轮播图 -->
        <div style="background-color: #FFFFFF;
    background-image: linear-gradient(180deg, #FFFFFF 0%, #6284FF 33%, #FF0000 66%, #ffffff 100%);">
            <el-carousel height="400px" trigger="click" class="carousel" type="card" :interval="5000">
                <el-carousel-item v-for="item in carousels" :key="item.gid">
                    <router-link :to="{name: 'introduce', query: {commodityId: item.gid}}">
                        <img :src="item.pthumbnail">
                    </router-link>
                </el-carousel-item>
            </el-carousel>
        </div>

        <div class="commodities">
            <!-- 商品类型 -->
            <el-row>
                <el-col :span="20" :offset="2" style="margin-bottom: 20px;">
                    <el-divider></el-divider>
                    <div style="margin: 0 20px;">
                        <el-button size="small" v-for="(item, index) in types" :key="index" :type="item.buttonType" @click="typeSelected(index)">
                            {{item.goods_TypeName}}
                        </el-button>
                    </div>
                </el-col>
            </el-row>
            <!-- 该商品类型下的所有商品 -->
            <el-row v-for="(row,index) in commodities" :key="index">
                <el-col :span="20" :offset="2">
                    <el-row :gutter="30">
                        <el-col :span="6" v-for="item in row" :key="item.gid">
                            <router-link :to="{name: 'introduce', query: {commodityId: item.gid}}" class="card">
                                <el-card :body-style="{ padding: '20px' }" shadow="hover">
                                    <img :src="item.pthumbnail"    style="width: 200px;height:200px"   >
                                    <div style="padding: 14px;">
                                        <span>{{item.name}}</span>
                                    </div>
                                    <div style="padding: 0 14px;">
                                        <span class="newPrice">￥{{item.goods_NewPrice}}</span>
                                        <span class="oldPrice">￥{{item.goods_OldPrice}}</span>
                                        <span class="commentCount">评价 {{item.commentCount}}</span>
                                    </div>
                                </el-card>
                            </router-link>
                        </el-col>
                    </el-row>
                    <el-divider></el-divider>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            form: {
                query: ''
            },
            carousels: [

            ],

            // 商品数组
            commodities:[

            ],

            types: [
                {
                    buttonType: 'primary',
                    goods_TypeName: '全部类型'
                }
            ]
        }
    },
    methods: {
        onSearch() {
            var that = this;

            this.$axios.get('/goods/searchGoods',{
                params: {
                    keyword: this.form.query
                }
            })
            .then(function (response) {
                //let search_name = document.getElementById("serch_good").value;
                //alert(search_name);

                that.commodities = [];
                console.log("搜索的商品列表为: ");
                var data = response.data.data;
                console.log(data);
                //console.log(data);
                let temp = [];
                for (let i = 0; i < data.length; i++) {
                    // 4个商品一行
                    if (temp.length == 4) {
                        that.commodities.push(temp);
                        temp = [];
                    }
                    //console.log(this.form.query);
                    //修改变量名
                    data[i].name = data[i].gname;
                    data[i].goods_OldPrice = data[i].gprice;
                    data[i].goods_NewPrice = data[i].gprice;
                    //data[i].pthumbnail = "http://202.193.52.12:8080/"+data[i].pthumbnail;
                    temp.push(data[i]);
                }
                that.commodities.push(temp);
            })
            .catch(function (error) {
                alert(" 出错了 ! ");
                console.log(error);
            });
        },

        typeSelected(index) {
            var that = this;
            // 点击全部类型
            if(index == 0){
                for (let i = 0; i < this.types.length; i++) {
                    this.types[i].buttonType = 'text';
                }
                this.types[index].buttonType = 'primary';
                this.$axios.get('/goods/selectAllGoods')
                .then(function (response) {
                    that.commodities = [];

                    console.log("选择的商品列表为: ");
                    var data = response.data.data;
                    console.log(data);

                    //console.log(data);
                    let temp = [];
                    for (let i = 0; i < data.length; i++) {
                        if (temp.length == 4) {
                            that.commodities.push(temp);
                            temp = [];
                        }
                        //修改变量名
                        data[i].name = data[i].gname;
                        data[i].goods_OldPrice = data[i].gprice;
                        data[i].goods_NewPrice = data[i].gprice;
                        //data[i].pthumbnail = "http://202.193.52.12:8080/"+data[i].pthumbnail;
                        temp.push(data[i])
                    }
                    that.commodities.push(temp);
                })
                .catch(function (error) {
                    console.log(error);
                });
            }
            // 点击相应商品类型
            else if(index == 1){
                for (let i = 0; i < this.types.length; i++) {
                    this.types[i].buttonType = 'text';
                }
                this.types[index].buttonType = 'primary';

                this.$axios.get('/goods/selectAllGoods')
                .then(function (response) {
                    that.commodities = [];

                    console.log("选择的商品列表为: ");
                    var data = response.data.data;
                    console.log(data);

                    //console.log(data);
                    let temp = [];
                    for (let i = 0; i < data.length; i++) {
                        if (temp.length == 4) {
                            that.commodities.push(temp);
                            temp = [];
                        }
                        let price =  data[i].gprice;
                        console.log( price );

                        if( price>=0 && price<500){
                            //修改变量名
                            data[i].name = data[i].gname;
                            data[i].goods_OldPrice = data[i].gprice;
                            data[i].goods_NewPrice = data[i].gprice;
                           //data[i].pthumbnail = "http://202.193.52.12:8080/"+data[i].pthumbnail;
                           temp.push(data[i]);
                        }
                    }
                    that.commodities.push(temp);
                })
                .catch(function (error) {
                    console.log(error);
                });
            }
            else if(index == 2){
                for (let i = 0; i < this.types.length; i++) {
                    this.types[i].buttonType = 'text';
                }
                this.types[index].buttonType = 'primary';

                this.$axios.get('/goods/selectAllGoods')
                .then(function (response) {
                    that.commodities = [];

                    console.log("选择的商品列表为: ");
                    var data = response.data.data;
                    console.log(data);

                    //console.log(data);
                    let temp = [];
                    for (let i = 0; i < data.length; i++) {
                        if (temp.length == 4) {
                            that.commodities.push(temp);
                            temp = [];
                        }
                        let price =  data[i].gprice;
                        //console.log( price );

                        if( price>=500 && price<1000){
                            //修改变量名
                            data[i].name = data[i].gname;
                            data[i].goods_OldPrice = data[i].gprice;
                            data[i].goods_NewPrice = data[i].gprice;
                           //data[i].pthumbnail = "http://202.193.52.12:8080/"+data[i].pthumbnail;
                           temp.push(data[i]);
                        }
                    }
                    that.commodities.push(temp);
                })
                .catch(function (error) {
                    console.log(error);
                });
            }
            else if(index == 3){
                for (let i = 0; i < this.types.length; i++) {
                    this.types[i].buttonType = 'text';
                }
                this.types[index].buttonType = 'primary';

                this.$axios.get('/goods/selectAllGoods')
                .then(function (response) {
                    that.commodities = [];

                    console.log("选择的商品列表为: ");
                    var data = response.data.data;
                    console.log(data);

                    //console.log(data);
                    let temp = [];
                    for (let i = 0; i < data.length; i++) {
                        if (temp.length == 4) {
                            that.commodities.push(temp);
                            temp = [];
                        }
                        let price =  data[i].gprice;
                        //console.log( price );

                        if( price>=1000 && price<2000){
                            //修改变量名
                            data[i].name = data[i].gname;
                            data[i].goods_OldPrice = data[i].gprice;
                            data[i].goods_NewPrice = data[i].gprice;
                           //data[i].pthumbnail = "http://202.193.52.12:8080/"+data[i].pthumbnail;
                           temp.push(data[i]);
                        }
                    }
                    that.commodities.push(temp);
                })
                .catch(function (error) {
                    console.log(error);
                });
            }
            else if(index == 4){
                for (let i = 0; i < this.types.length; i++) {
                    this.types[i].buttonType = 'text';
                }
                this.types[index].buttonType = 'primary';

                this.$axios.get('/goods/selectAllGoods')
                .then(function (response) {
                    that.commodities = [];

                    console.log("选择的商品列表为: ");
                    var data = response.data.data;
                    console.log(data);

                    //console.log(data);
                    let temp = [];
                    for (let i = 0; i < data.length; i++) {
                        if (temp.length == 4) {
                            that.commodities.push(temp);
                            temp = [];
                        }
                        let price =  data[i].gprice;
                        //console.log( price );

                        if( price>=2000 && price<4000){
                            //修改变量名
                            data[i].name = data[i].gname;
                            data[i].goods_OldPrice = data[i].gprice;
                            data[i].goods_NewPrice = data[i].gprice;
                           //data[i].pthumbnail = "http://202.193.52.12:8080/"+data[i].pthumbnail;
                           temp.push(data[i]);
                        }
                    }
                    that.commodities.push(temp);
                })
                .catch(function (error) {
                    console.log(error);
                });
            }
            else if(index == 5){
                for (let i = 0; i < this.types.length; i++) {
                    this.types[i].buttonType = 'text';
                }
                this.types[index].buttonType = 'primary';

                this.$axios.get('/goods/selectAllGoods')
                .then(function (response) {
                    that.commodities = [];

                    console.log("选择的商品列表为: ");
                    var data = response.data.data;
                    console.log(data);

                    //console.log(data);
                    let temp = [];
                    for (let i = 0; i < data.length; i++) {
                        if (temp.length == 4) {
                            that.commodities.push(temp);
                            temp = [];
                        }
                        let price =  data[i].gprice;
                        //console.log( price );

                        if( price>=4000 ){
                            //修改变量名
                            data[i].name = data[i].gname;
                            data[i].goods_OldPrice = data[i].gprice;
                            data[i].goods_NewPrice = data[i].gprice;
                           //data[i].pthumbnail = "http://202.193.52.12:8080/"+data[i].pthumbnail;
                           temp.push(data[i]);
                        }
                    }
                    that.commodities.push(temp);
                })
                .catch(function (error) {
                    console.log(error);
                });
            }
        }

    },
    // 获取数据
    created() {
        var that = this;
        // 获取类型信息
            let type = [
                {
                    buttonType: 'text',
                    goods_TypeName: '0~500'
                },
                {
                    buttonType: 'text',
                    goods_TypeName: '500~1000'
                },
                {
                    buttonType: 'text',
                    goods_TypeName: '1000~2000'
                },
                {
                    buttonType: 'text',
                    goods_TypeName: '2000~4000'
                },
                {
                    buttonType: 'text',
                    goods_TypeName: '4000+'
                }
            ];
            for(let i=0;i<type.length;i++){
                that.types.push(type[i]);
            }
            console.log(that.types);
        // this.$axios.get('/goods/list')
        // .then(function (response) {

        //     for (let i = 0; i < data.length; i++) {
        //         data[i].buttonType = 'text';
        //         data[i].goods_TypeName = data[i].id[0];
        //         that.types.push(data[i]);
        //     }
        // })
        // .catch(function (error) {
        //     console.log(error);
        // });

        // 获取轮播图 商品列表
        this.$axios.get('/goods/selectAllGoods')
        .then(function (response) {
            console.log("所有商品列表为: ");
            var data = response.data.data;
            console.log(data);
            // 轮播图
            for (let i = 0; i < 5; i++) {
                if (i == data.length) {
                    break;
                }
                that.carousels.push(data[i]);
            }
            //console.log(that.carousels);
            // 商品列表
            let temp = [];
            for (let i = 0; i < data.length; i++) {
                if (temp.length == 4) {
                    that.commodities.push(temp);
                    temp = [];
                }
                //修改变量名
                data[i].name = data[i].gname;
                data[i].goods_OldPrice = data[i].gprice;
                data[i].goods_NewPrice = data[i].gprice;
                data[i].commentCount = Math.round(Math.random()*10000);
                temp.push(data[i]);
                console.log("temp为: ");
                console.log(data[i]);
            }
            that.commodities.push(temp);
            console.log("commodities为: ");
            console.log(that.commodities);
        })
        .catch(function (error) {
            console.log(error);
        });
    },
}
</script>

<style scoped>
.form {
    text-align: center;
}

.input {
    width: 500px;
}

.carousel {
    width: 800px;
    margin: 0 auto;
}

.carousel img {
    width: 400px;
    height: 400px;
    /* 缩放 */
    object-fit: contain;
}

.commodities img {
    display: block;
    margin: 0 auto;
}

.card {
    text-decoration: none;
}

.newPrice {
    color: #F56C6C;
    font-size: 24px;
}

.oldPrice {
    color: #909399;
    text-decoration: line-through;
}

.commentCount {
    float: right;
    margin-top: 10px;
    color: #909399;
    font-size: 13px;
}

.el-button--text {
    color: black;
}
</style>
