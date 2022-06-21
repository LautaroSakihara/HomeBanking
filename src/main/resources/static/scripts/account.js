Vue.createApp({
    data() {
        return {          
            clients: {},
            accountTransactions: [],
            account:[],
            accountLoans:[],
            transactionsPdf:[],
            options: {
                method: "GET"
            }
        }
    },

    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const idAccount = urlParams.get('id');
        
        axios.get("http://localhost:8080/api/accounts/" + idAccount)
        
        .then(data => {
            this.accountTransactions = data.data.transactions.sort(function(a, b){return b.id - a.id})
            this.account = data.data
            console.log(this.account.number)
           
        })
        
        axios.get("http://localhost:8080/api/clients/current")
        .then(data => {
            this.clients = data.data
            this.accountLoans = this.clients.loans
           
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
          exportPDF() {

            this.accountTransactions.forEach(transaction => {

              let fila = {
                  DATE: `${transaction.date}`, 
                  description: `${transaction.description}`, 
                  TYPE: `${transaction.type}`, 
                  amount: `${transaction.amount}`
              }
               this.transactionsPdf.push(fila);
           });

           
      
            var columns = [
              {title: "DATE", dataKey: "DATE"},
              {title: "TYPE", dataKey: "TYPE"},
              {title: "Balance", dataKey: "amount"},
              {title: "Description", dataKey: "description"},
            ];
            var doc = new jsPDF('p', 'pt');
            doc.text(`${this.account.number} transactions list from ${this.fromDate} to ${this.toDate}`, 40, 40);
            doc.autoTable(columns, this.transactionsPdf, {
              margin: {top: 60},
            });
            doc.save('todos.pdf');
          },
    }




}).mount("#app");