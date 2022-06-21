const app = Vue.createApp({

  data() {
    return {
      clients: {},
      accounts: [],
      accountBalance: {},
      type: [],
      amountMax:"",
      description: "",
      amount: "",
      paymentSelected:[],
      loans:[],
      paymentsList:[],
      payments:"",
      loanId:[],
      loanType:[],
      accountNumberDestiny:"",
      options: {
        method: "GET"
      }
    }
  },
  created() {
    axios.get("http://localhost:8080/api/clients/current")
    
    .then(data => {
      this.clients = data.data
      
      this.accounts = this.clients.account
      document.querySelector("#loader").classList.toggle("loader2") 
      console.log(this.clients)
      
    })
    
    axios.get("http://localhost:8080/api/loans")
    
    .then(data => {
      this.loans = data.data   
      console.log(this.loans)
      
      })
  },
  methods: {

    logout() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"))
        .then(() => window.location.replace("http://localhost:8080/web/index.html"))
    },
    createLoan() {
      Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, create Loan!'

      }).then((result) => {
        if (result.isConfirmed) {
          axios.post('/api/loans', {"loanId": `${this.loanId}`,"amount": `${this.amount}`,"payments": `${this.paymentSelected}`,"accountNumberDestiny": `${this.accountNumberDestiny}`}, 
            { headers: {"Content-Type": "application/json"}})
            .catch(error => {
              Swal.fire({
                icon: 'error',
                text: error.response.data,

              })
            })
          Swal.fire(

            'Loan Succesfull!',
            'Your Loan has been created.',
            'success'
          )

        }

      })

    },
    accountsFilter() {
      return this.accounts.filter(account => account.number != this.numberOwn)
    },
    
      filterPayments(){
      let availableLoans = this.loans
      this.payments = availableLoans.filter(loan => loan.name == this.loanType)[0].payments;
      this.loanId = availableLoans.filter(loan => loan.name == this.loanType)[0].id;
      this.amountMax = this.loans.filter(loan => loan.id == this.loanId)[0].maxAmount;
      console.log(this.payments)
      console.log(this.loanId)
    }

  }

}).mount("#app");