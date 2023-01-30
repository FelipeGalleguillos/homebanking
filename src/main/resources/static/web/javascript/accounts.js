
Vue.createApp({
    data(){
        return{
            accountType:'',
            data:[],
            msg:''
        }
    },
    created(){
        this.loadData()
    },
    methods:{
        loadData(){
            axios.get('/api/clients/current')
            .then(data=>{
                this.data = data.data
                console.log(this.data);
            })
            .catch(err=>console.log(err))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => {
                window.location.href = "/web/index.html"
            })
        },
        createAccount(type){
            axios.post(`/api/clients/current/accounts/${type}`)
            .then(response=> window.location.reload())
            .catch(err=>console.log(err))
        },
        deleteAccount(account){
            console.log(account)
            axios.delete(`/api/accounts/${account.id}`)
            .then(res=>{
                console.log(res)
                window.location.reload()
            })
            .catch(err=>{
                this.msg=err.response.data
                setTimeout(() => {this.msg=''}, 2000);
            })
        }
    }
}).mount('#app')