package com.mp.showandtell.domain

 class CreditInput {
  var name: String = " "
  var address: String = " "
  var postCode: String = " "
  var phoneNumber: String = " "
  var creditLimit: String = " "
  var birthDate: String = " "

}

object CreditMain {
 // Main method
 def main(args: Array[String]): CreditInput = {
  // Class object
  var obj = new CreditInput()
  obj.name = args.apply(0) + args.apply(1)
  obj.address = args.apply(2)
  obj.postCode = args.apply(3)
  obj.phoneNumber = args.apply(4)
  obj.creditLimit = args.apply(5)
  obj.birthDate = args.apply(6)

  obj
 }
}