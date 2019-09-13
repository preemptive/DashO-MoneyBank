using System;
using System.Collections.Generic;
using MoneyBankServer.Models;

namespace MoneyBankServer.Responses
{
    public class AccountsResponse
    {
        public IEnumerable<Account> Accounts { get; set; }
    }
}
