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
        axios.get('http://localhost:8080/api/clients/current')
        .then(data=>{
            this.data=data.data
        })
        .catch(err=>console.log(err))
    },
    methods:{
        logout(){
            axios.post('/api/logout')
            .then(response => {
                window.location.href = "http://localhost:8080/web/index.html"
            })
        },
        create(){
            axios.post('http://localhost:8080/api/clients/current/cards',`type=${this.cardType}&color=${this.cardColor}`)
            .then(response=>window.location.href = "http://localhost:8080/web/cards.html")
            .catch(err=>{
                this.msg=err.response.data
            })
        }
    }
}).mount('#app')