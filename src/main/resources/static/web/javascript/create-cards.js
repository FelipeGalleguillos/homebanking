Vue.createApp({
    data(){
        return{
            data:[],
            cardType:'',
            cardColor:'',
            msg:''
        }
    },
    created(){
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
        create(){
            axios.post('/api/clients/current/cards',`type=${this.cardType}&color=${this.cardColor}`)
            .then(response=>window.location.href = "/web/cards.html")
            .catch(err=>{
                this.msg=err.response.data
            })
        }
    }
}).mount('#app')