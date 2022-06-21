Vue.createApp({
  data() {
    return {
      password: "",
      email: "",
      change:"",
      firstName:"",
      lastName:"",
      error:"",
      options: {
        method: "GET",
      },
    };
  },
  created() {
    
  },

  methods: {
    login() {
      axios.post("/api/login", `email=${this.email}&password=${this.password}`, {
          headers: { "content-type": "application/x-www-form-urlencoded" },    
        })
        
        .then((response) => console.log("signed in!!!"))
        .then(()=>window.location.replace("/web/accounts.html"))
        
        .catch(error =>{
          this.error="Incorrect Username or Password!!!"
        })   
         
        
     
    },

    logout() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"))
        .then(()=>window.location.replace("/web/index.html"))
    },
    createAccount() {
      axios
        .post("/api/clients/current/accounts") 
        .then(()=>location.reload())
        .catch(error =>console.warn(error))
    },
    register() {
      axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
      {headers:{'content-type':'application/x-www-form-urlencoded'}})
      .then(() => {
        this.login()
        this.createAccount()
      })
      .catch(error =>console.warn(error))
    }
  },
}).mount("#app");
