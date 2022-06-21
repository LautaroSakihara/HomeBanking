
const app = Vue.createApp({
    
    data() {
        return {
            clients: {},
            cards: [],
            debit: [],
            credit: [],
            type:"",
            color:"",
            options: {
                method: "GET"
            }
        }
    },
    created() {
        
        axios.get("http://localhost:8080/api/clients/current")

            .then(data => {
                this.clients = data.data

                this.cards = data.data.cards.sort(function (a, b) {
                    return a.id - b.id
                })
                this.accountCards = this.clients.cards
                this.credit= this.accountCards.filter(card => card.type =='CREDITO')
                this.debit= this.accountCards.filter(card => card.type =='DEBITO')
                console.log(this.clients.cards)
                console.log(this.debit)
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
          createCard(){
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, create it!'

              }).then((result) => {
                if (result.isConfirmed) {
                axios.post("/api/clients/current/cards", `type=${this.type}&color=${this.color}`, {
                headers: { "content-type": "application/x-www-form-urlencoded" },    
              })  .catch(error => {
                Swal.fire({
                  icon: 'error',
                  text: error.response.data,
  
                })
              })   
                  Swal.fire(
                    
                    'Created!',
                    'Your card has been created.',
                    'success'
                  )
                  
                }
              })
            
        }
        
    }
    
}).mount("#app");