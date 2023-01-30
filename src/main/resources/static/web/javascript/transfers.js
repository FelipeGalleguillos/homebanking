Vue.createApp({
    data(){
        return{
            data:[],
            accounts:[],
            msg:'',
            originAcc:'',
            destinyAcc:'',
            description:'',
            amount:''
        }
    },
    created(){
        this.loadData()
    },
    methods:{
        loadData(){
            axios.get('http://localhost:8080/api/clients/current')
            .then(data=>{
                this.data = data.data
                this.accounts=this.data.accounts
                console.log(this.data);
            })
            .catch(err=>console.log(err))
        },
        transfer(){
            axios.post('/api/transactions',`fromNumber=${this.originAcc}&toNumber=${this.destinyAcc}&amount=${this.amount}&description=${this.description}`)
            .then(response=>{
                window.location.reload()
                console.log(response)
            })
            .catch(err=>{
                console.log(err)
                this.msg=err.response.data})

        },
        logout(){
            axios.post('/api/logout')
            .then(response => {
                window.location.href = "http://localhost:8080/web/index.html"
            })
            .catch(err=>console.log(err))
        },
    }
}).mount('#app')