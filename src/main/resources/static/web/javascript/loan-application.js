Vue.createApp({
    data(){
        return{
            data:[],
            loans:[],
            loan:[],
            amount:null,
            payment:'',
            accNumber:'',
            msg:''
        }
    },
    created(){
        axios.get('/api/loans')
        .then(data=>{
            this.loans=data.data
            console.log(this.loans)
        })
        .catch(err=>console.log(err))

        axios.get('/api/clients/current')
        .then(data=>{
            this.data=data.data
        })
        .catch(err=>console.log(err))
    },
    methods:{
        logout(){
            axios.post('/api/logout')
            .then(response => {
                window.location.href = "/web/index.html"
            })
        },
        apply(){
            axios.post('/api/loans',{loanId:this.loan.id,amount:this.amount,payment:this.payment,accNumber:this.accNumber})
            .then(response=>{
                window.location.reload()
                console.log(response)
            })
            .catch(err=>{
                this.msg=err.response.data
            })
        },
        logout(){
                    axios.post('/api/logout')
                    .then(response => {
                        window.location.href = "/web/index.html"
                    })
                },
    }
}).mount('#app')