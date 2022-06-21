Vue.createApp({
    data() {
        return {
            dataBase: [],
            dataJson:[],
            client:{},
            clients: {},
            firstName: "",
            lastName: "",
            email: "",
            firstNameEdit: "",
            lastNameEdit: "",
            emailEdit: "",
            options: {
                method: "GET"
            }
        }
    },
    created() {
        axios.get("http://localhost:8080/rest/clients")
            
            .then(data => {
                
                this.dataBase = data.data._embedded.clients
                this.dataJson = data.data
                console.log(this.dataBase)
            })
            axios.get("http://localhost:8080/api/clients/current")
            .then(data => {
                this.clients = data.data
                console.log(this.clients.firstName)
            })
    },

    methods: {
        addClient() {
            if(this.firstName != "" && this.lastName != "" && this.email !="" && this.email.includes("@") && this.email.includes(".")){
            this.client = {
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
            }
            console.log(this.client)
            axios.post("http://localhost:8080/rest/clients",this.client)
            location.reload()
        }
        },

        deleteClient(client) {
            console.log(client)
            console.log(client._links.client.href)
            let id = client._links.client.href
            axios.delete(id) 
            location.reload()
        },

        editClient(client){
            let id = client._links.client.href
            client = {
                firstName: this.firstNameEdit,
                lastName: this.lastNameEdit,
                email: this.emailEdit,
            } 
            console.log(client)
            axios.put(id,client)
            location.reload()
        }
    }
}).mount("#app");