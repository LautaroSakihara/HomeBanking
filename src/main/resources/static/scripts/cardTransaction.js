/* 
const app = Vue.createApp({
    
    data() {
        return {
            clients: {},
            cards: [],
            debit: [],
            credit: [],
            accounts: [],
            accountBalance:{},
            accountCards:[],
            id: "",  
            type:"",
            description:"",
            thruDate:"",
            cardHolder:"",
            cvv:"",
            amount:"",
            numberOwn:[],
            numberDestiny:[],
            options: {
                method: "GET"
            }
        }
    },
    created() {
        
        axios.get("http://localhost:8080/api/clients/current")

            .then(data => {
                this.clients = data.data
                this.accountCards = this.clients.cards
                this.accounts = this.clients.account
                
                console.log(this.accounts[0].balance)
                document.querySelector("#loader").classList.toggle("loader2") 
            })
        },
    methods: {
        logout() {
            axios
              .post("/api/logout")
              .then((response) => console.log("signed out!!!"))
              .then(()=>window.location.replace("http://localhost:8080/web/index.html"))
          },
          createTransaction(){
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, transfer it!'

              }).then((result) => {
                if (result.isConfirmed) {
                axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&numberOwn=${this.numberOwn}&numberDestiny=${this.numberDestiny}`, {
                headers: { "content-type": "application/x-www-form-urlencoded" }, 
                   
              })
              .catch(error =>{Swal.fire({
                icon: 'error',
                text: error.response.data,
                
              })})    
                  Swal.fire(
                    
                    'Transferred!',
                    'Your amount has been transferred.',
                    'success'
                  )
                  
                }
                
              })
              
        },
        accountsFilter(){
          return this.accounts.filter(account => account.number != this.numberOwn)
        },
        balance(){
          this.accountBalance=this.accounts.filter(account => account.number == this.numberOwn)[0].balance
          console.log(this.accountBalance)
        },
        cardTransactionCredit() {
            Swal.fire({
                title: 'Do you want to do the transaction?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.patch('/api/cardTransaction', `{"cardType": "CREDIT","amount": ${this.amount},"cardNumber": "${this.cardNumber}","cardHolder": "${this.cardHolder}","cvv": "${this.cvv}","thruDate": "${this.thruDate}","description": "${this.description}","accountNumber": "${this.numberOwn}"}`, { headers: { "Content-Type": "application/json" } }).then(
                        Swal.fire('Transfered!', '', 'success').then(() => window.location.replace("http://localhost:8080/web/Cards.html")))
                        .catch(error => {
                            Swal.fire({
                                icon: 'error',
                                title: error.response.data,
                                timer: 2000,
                            })
                        })
                }
            })
        },
        
    }
    
}).mount("#app"); */