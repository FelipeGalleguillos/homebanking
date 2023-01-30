
Vue.createApp({
    data(){
        return{
            data:[],
            credit:[],
            debit:[],
            nowDate:""
        }
    },
    created(){
        axios.get('http://localhost:8080/api/clients/current')
        .then(data=>{
            this.data=data.data
            this.debit=data.data.cards.filter(card=>card.type=="DEBITO")
            .map(card=>{
                return{
                    id:card.id,
                    number:card.number,
                    cardHolder:card.cardHolder,
                    type:card.type,
                    color:card.color,
                    fromDate:card.fromDate.substring(2,7),
                    thruDate:card.thruDate.substring(2,7),
                    cvv:card.cvv
                }
            })
            this.credit=data.data.cards.filter(card=>card.type=="CREDITO")
            .map(card=>{
                return{
                    id:card.id,
                    number:card.number,
                    cardHolder:card.cardHolder,
                    type:card.type,
                    color:card.color,
                    fromDate:card.fromDate.substring(2,7),
                    thruDate:card.thruDate.substring(2,7),
                    cvv:card.cvv
                }
            })
            let date = new Date()
            this.nowDate = date.getFullYear()+"-0"+(date.getMonth()+1)
            console.log(this.nowDate)
        })
        .catch(err=>console.log(err))
    },
    methods:{
        deleteCard(card){
            console.log(card)
            axios.delete(`http://localhost:8080/api/cards/${card.id}`)
            .then(res=>{
                console.log(res)
                window.location.reload()
            })
            .catch(err=>{console.log(err)})
        },
        logout(){
            axios.post('/api/logout')
            .then(response => {
                window.location.href = "http://localhost:8080/web/index.html"
            })
        }
    }
}).mount('#app')