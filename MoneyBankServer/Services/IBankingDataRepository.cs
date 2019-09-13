using System;
using System.Collections.Generic;
using MoneyBankServer.Models;
namespace MoneyBankServer.Services
{
    public interface IBankingDataRepository
    {
        IList<Account> GetAccounts(string username);
    }
}
