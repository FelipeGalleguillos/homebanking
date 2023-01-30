
Vue.createApp({
    data() {
        return {
            clients: [],
            accounts:[],
            transactions:[],
            json: [],
            firstName: '',
            lastName: '',
            email: '',
            url:''
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get('/rest/clients')
                .then(data => {
                    this.json = data.data
                    this.clients = this.json._embedded.clients
                    console.log(this.clients)
                })
                .catch(err => console.log(err))
        },
        addClient() {
            axios.post('/rest/clients', {
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email
            })
            .then(res=>this.loadData())
            .catch(err=>console.log(err))

        },
        deleteClient(client){ 
            let cliUrl = client._links.self.href
            axios.get(client._links.accounts.href)
            .then(res=>{
                this.accounts=res.data._embedded.accounts
                if(this.accounts.length==0){
                    axios.delete(cliUrl)
                    .then(res=> this.loadData())
                    .catch(err=>console.log(err))
                }else{
                    this.accounts.forEach(account=>{
                        axios.get(account._links.transactions.href)
                        .then(res=>{
                            let transactionsArray=[]
                            transactionsArray=res.data._embedded.transactions
                            transactionsArray.forEach(transaction=>this.transactions.push(transaction))
                        })
                        .catch(err=>console.log(err))
                    })
                    this.transactions.forEach(transaction=>{
                        axios.delete(transaction._links.self.href)
                        .then(res=>console.log("transaction deleted"))
                        .catch(err=>console.log(err))
                    })
                    this.accounts.forEach(account=>{
                        axios.delete(account._links.self.href)
                        .then(res=>console.log("account deleted"))
                        .catch(err=>console.log(err))
                    })
                    axios.delete(cliUrl)
                    .then(res=> this.loadData())
                    .catch(err=>console.log(err))
                }
            })
            .catch(err=>console.log(err))
        }
    }

}).mount('#app')