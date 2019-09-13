using System;
using System.Collections.Generic;
using System.Linq;

namespace MoneyBankServer.Services
{
    public class StaticLoginRepository : ILoginRepository
    {
        private readonly ISet<Login> Logins = new HashSet<Login> 
        { 
            new Login {
                Username = "steve",
                Password = "password",
                CustomerName = "Steve Lemon"
            }
        };
        public Login GetLogin(string username, string password)
        {
            return Logins.FirstOrDefault(x => x.Username == username 
                                         && x.Password == password);
        }
    }
}
