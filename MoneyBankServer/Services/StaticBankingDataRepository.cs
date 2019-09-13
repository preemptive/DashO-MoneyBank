using System;
using System.Collections.Generic;
using MoneyBankServer.Models;

namespace MoneyBankServer.Services
{
    public class StaticBankingDataRepository : IBankingDataRepository
    {
        private static readonly IDictionary<string, IList<Account>> accounts = new Dictionary<string, IList<Account>>
        {
            { 
                "steve", 
                new List<Account> 
                {
                    new Account {
                        AccountNumber = "3434536655",
                        Balance = 14455.33
                    },
                    new Account {
                        AccountNumber = "7667778776",
                        Balance = 5443.54
                    }
                } 
            }
        };

        public IList<Account> GetAccounts(string username)
        {
            return accounts[username];
        }
    }
}
