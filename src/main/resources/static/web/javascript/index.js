
Vue.createApp({
    data(){
        return{
            firstName:"",
            lastName:"",
            email:"",
            pwd:"",
            msg:'',
            loginSuccess:null,
            showLogForm:null,
            showSignForm:null
        }
    },
    created(){
        this.showLogForm=true
        this.showSignForm=false
    },
    methods:{
        signIn(){
            axios.post('/api/login',`email=${this.email}&password=${this.pwd}`)
            .then(response=>{
                console.log(response)
                if(this.email=="admin@mindhub.com"){
                    window.location.href = "http://localhost:8080/manager.html"
                }else{
                    window.location.href = "http://localhost:8080/web/accounts.html"
                }
            })
            .catch(err=>{
                this.msg = err.response.data.error
                this.name=""
                this.pwd=""
            })
        },
        signUp(){
            axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.pwd}`)
            .then(response=>{
                this.showLogForm=false
                this.showSignForm=false
                axios.post('/api/login',`email=${this.email}&password=${this.pwd}`)
                .then(response=>{
                    setTimeout(() => {this.loginSuccess=true}, 3000);
                window.location.href = "http://localhost:8080/web/accounts.html"
                })
                .catch(err=>{
                    console.log(err)
                    this.msg = err.response.data
                })
            })
            .catch(err=>{
                console.log(err)
                this.msg = err.response.data
            })
        },
        cleanMsg(){
            this.msg=""
        },
        logForm(){
            this.msg=""
            this.showLogForm=true
            this.showSignForm=false
        },
        signForm(){
            this.msg=""
            this.showLogForm=false
            this.showSignForm=true
        }
        
    }
}).mount('#app')