Vue.createApp({
    data() {
        return {
            clients: {},
            accounts: [],
            accountLoans: [],
            cards: [],
            id:"",
            options: {
                method: "GET"
            }
        }
    },
    created() {
        axios.get("/api/clients/current")

            .then(data => {
                this.clients = data.data

                this.accounts = data.data.account.sort(function (a, b) {
                    return a.id - b.id
                })
                this.cards = data.data.cards.sort(function (a, b) {
                    return a.id - b.id
                })
                this.accountLoans = this.clients.loans
                /* console.log(this.clients.loans) */
                document.querySelector("#loader").classList.toggle("loader2") 
            })
    },

    methods: {
        createAccount() {
            axios
              .post("/api/clients/current/accounts") 
              .then(()=>location.reload())
              .catch(error =>console.warn(error))
          },
          deletAccount() {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete Account!'
        
              }).then((result) => {
                if (result.isConfirmed) {
                    (axios.patch('/api/clients/current/accounts', `id=${this.id}`, {
                        headers: {
                            'content-type': 'application/x-www-form-urlencoded'
                        }
                    })).then(()=>window.location.replace("/web/accounts.html"))
                    .catch(error => {
                      Swal.fire({
                        icon: 'error',
                        text: error.response.data,
        
                      })
                    })
                  Swal.fire(
        
                    'Account Deleted Succesfully!',
                    'Your Account has been deleted.',
                    'success'
                  )
        
                }
        
              })

        },
        logout() {
            axios
              .post("/api/logout")
              .then((response) => console.log("signed out!!!"))
              .then(()=>window.location.replace("/web/index.html"))
          },
    }

}).mount("#app");