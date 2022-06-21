Vue.createApp({
    data() {
        return {
            clients: {},
            cards: [],
            debit: [],
            credit: [],
            type: "",
            color: "",
            id: "",
            valueSelected: [],
            accountCards: [],
            options: {
                method: "GET"
            }
        }
    },
    created() {
        axios.get("/api/clients/current")

            .then(data => {
                document.querySelector("#loader").classList.toggle("loader2")
                this.clients = data.data

                this.cards = data.data.cards.sort(function (a, b) {
                    return a.id - b.id
                })
                this.accountCards = this.clients.cards
                this.cardTransaction= this.accountCards
                this.credit = this.accountCards.filter(card => card.type == 'CREDITO')
                this.debit = this.accountCards.filter(card => card.type == 'DEBITO')
              /*   console.log(this.cardTransaction) */
                

                const form = document.querySelector("form")

                form.addEventListener("change", () => {

                    //agarra todos los checkboxs con querySelectorAll
                    let selectCheckbox = document.querySelectorAll("input[type='checkbox']")
                    //Convertir el Nodelist a un array para poder utilizar el filter
                    let arrayCheckbox = Array.from(selectCheckbox)
                    //filtrar el array dependiendo si los checkboxes estan checkeados o no
                    let checkboxSelected = arrayCheckbox.filter(checkbox => checkbox.checked === true)
                    //Se obtiene el valor de los checkboxes seleccionados utilizando map ("DEBITO","CREDITO")
                    let valueSelected = checkboxSelected.map(checkbox => checkbox.value)

                   /*  console.log(valueSelected) */

                    this.credit = this.accountCards.filter(card => card.type == 'CREDITO')
                    this.debit = this.accountCards.filter(card => card.type == 'DEBITO')
                })
            })
    },

    methods: {
        logout() {
            axios
                .post("/api/logout")
                .then((response) => console.log("signed out!!!"))
                .then(() => window.location.replace("http://localhost:8080/web/index.html"))
        },
        deletCard() {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete Card!'
        
              }).then((result) => {
                if (result.isConfirmed) {
                    (axios.patch('/api/clients/current/cards', `id=${this.id}`, {
                        headers: {
                            'content-type': 'application/x-www-form-urlencoded'
                        }
                    })).then(()=>window.location.replace("http://localhost:8080/web/cards.html"))
                    .catch(error => {
                      Swal.fire({
                        icon: 'error',
                        text: error.response.data,
        
                      })
                    })
                  Swal.fire(
        
                    'Card Deleted Succesfully!',
                    'Your Card has been deleted.',
                    'success'
                  )
        
                }
        
              })
           

        },
        createCard() {
            axios.post("/api/clients/current/cards", `type=${this.type}&color=${this.color}`, {
                headers: {
                    "content-type": "application/x-www-form-urlencoded"
                },
            })
        }
    }

}).mount("#app");