<Query Kind="Statements">
  <Output>DataGrids</Output>
</Query>

string s = Console.ReadLine();

string[] sArr = s.Split(new string[] {"&", "="}, StringSplitOptions.None);

//sArr.Dump();

Console.WriteLine(sArr[1]);