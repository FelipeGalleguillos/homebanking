
Vue.createApp({
    data() {
        return {
            account: [],
            transactions: [],
            filteredTransactions: [],
            client: [],
            data: [],
            fromDate: '',
            toDate: ''
        }
    },
    created() {
        axios.get('/api/clients/current')
            .then(res => this.client = res.data)
            .catch(err => console.log(err))

        const queryString = location.search
        const id = new URLSearchParams(queryString).get("id")
        axios.get("/api/accounts/" + id)
            .then(data => {
                const formatter = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', })
                this.account = data.data
                console.log(this.account)
                this.transactions = data.data.transactions
                    .map(transaction => {
                        return {
                            type: transaction.type,
                            description: transaction.description,
                            amount: formatter.format(transaction.amount),
                            accountBalance: transaction.accountBalance,
                            date: transaction.date.substring(0, 10),
                            time: transaction.date.substring(11, 19)
                        }
                    })
                this.filteredTransactions = this.transactions
                console.log(this.transactions);
            })
            .catch(err => console.log(err))
    },
    methods: {
        logout() {
            axios.post('/api/logout')
                .then(response => {
                    window.location.href = "/web/index.html"
                })
        },
        createPDF() {
            if (this.fromDate.length > 0 && this.toDate.length > 0) {
                let from = this.fromDate + " " + "00:00:00.00000"
                let to = this.toDate + " " + "23:59:59.00000"
                // axios.get('http://localhost:8080/api/transactions/export/pdf',{"id":this.account.id,"fromDate":from,"toDate":to})
                // .then(data =>{console.log(data)})
                // .catch(err=>console.log(err))
                axios({
                    url: '/api/transactions/export/pdf',
                    method: 'GET',
                    params: {
                        id:this.account.id,
                        fromDate:from,
                        toDate:to
                    },
                    responseType: 'blob',
                }).then((response) => {
                    const href = URL.createObjectURL(response.data)
                    const link = document.createElement('a')
                    link.href = href
                    link.setAttribute('download', 'transactions.pdf')
                    document.body.appendChild(link)
                    link.click()
                })
            } else {
                const { jsPDF } = window.jspdf;
                const DOC = new jsPDF('p', 'pt', 'letter')
                const width = DOC.internal.pageSize.getWidth()
                DOC.text(width / 2 - 70, 40, "TRANSACTIONS");
                DOC.autoTable({ html: '#table', startY: 60 })
                DOC.save('transactions-table.pdf')
                // const { jsPDF } = window.jspdf;
                // const DOC = new jsPDF('p', 'pt', 'letter')
                // const width = DOC.internal.pageSize.getWidth()
                // DOC.text(width/2-70,80, "TRANSACTIONS");
                // DOC.text(width/2-120,40, "Account:"+ this.account.number);
                // DOC.text(width/2+20,40, "Balance:"+ this.account.balance);
                // DOC.autoTable({html:'#table',startY:100})
                // DOC.save('transactions-table.pdf')

            }
        },
        filter() {
            if (this.fromDate.length > 0 && this.toDate.length > 0) {
                this.filteredTransactions = this.transactions.filter(transaction => transaction.date >= this.fromDate && transaction.date <= this.toDate)
            } else {
                this.filteredTransactions = this.transactions
            }
        }
    }
}).mount('#app')