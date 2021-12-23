# yebCMS
云E办管理系统
技术：jdk1.8 + springboot + maven + Jwt + MySQL + MyBatis + 

功能：登录api+token认证

swagger-ui API: http://localhost:8080/swagger-ui/index.html


public key:
-----BEGIN CERTIFICATE-----
MIIDYzCCAkugAwIBAgIEVgvHtDANBgkqhkiG9w0BAQsFADBiMQswCQYDVQQGEwJa
SDEQMA4GA1UECBMHVW5rbm93bjERMA8GA1UEBxMIU2hhbmdoYWkxEDAOBgNVBAoT
B1Vua25vd24xEDAOBgNVBAsTB1Vua25vd24xCjAIBgNVBAMTAWwwHhcNMjEwMzA0
MTA1MjMyWhcNMjEwNjAyMTA1MjMyWjBiMQswCQYDVQQGEwJaSDEQMA4GA1UECBMH
VW5rbm93bjERMA8GA1UEBxMIU2hhbmdoYWkxEDAOBgNVBAoTB1Vua25vd24xEDAO
BgNVBAsTB1Vua25vd24xCjAIBgNVBAMTAWwwggEiMA0GCSqGSIb3DQEBAQUAA4IB
DwAwggEKAoIBAQCnP8tNAAPMni4T+Qs8NQbKycLZJP2x8LLkmkeNDAt5oTVMscZh
1JcD46VVh8xKqqn1BYNFw4BKswscnfJIMUuDfLWlaubuiC9KylkUAAyjuAYp+3IL
MoMX41g/35VGXCf1PPy1Mceqm4eKMG3cPAqN+2rATCzgCRidt1dg/4UURScxUkKZ
b6xp0DUqW3xosSN5hSQBT/EUSBCk7TBQPnD+cwbeFGWw8uOXgkc83uajnBNgzC+q
GJxiJ8Trtfm9qiIvlRnWZiyORnPrnhIm+IYtFIRanaW/EoPBsp5AgV8P44mc9JU1
mgqI5+bW56pbLugqagND6rxAXSxdn+nMKFjZAgMBAAGjITAfMB0GA1UdDgQWBBQk
VHFC2WhDbdm/XQsg/tuHaCez1jANBgkqhkiG9w0BAQsFAAOCAQEAbGafoQqFEklU
Xr+rOd0AqZGPNZ05Ya96h8pD8nsOCv44QmZPoyc21iOu0kp8Z5hsTVQSrYfmX+B1
YZTO+w48M/c2VymHkoBdQztsYGw353iZoaSBeeagqI4QAbbC97HAd/PD5myupjZo
BFjkp5s8SQnfWPIwF/G4FqF4C3zkVdG7YJN6MDrY8BBRIUNUl/Rra/8uR4WPCe2Q
IlsIlfDgc2yXWMuKgvCTJB1iYAmPKJhRVfFNiSTlYr/wPY/Qt1xKlvG+cS3DziF4
/f5PJxipiJq/E4/yTMcKnHPC5vIaeKLdUjH6wTkufAUukIO8+x5jRJxYqKDi52aY
2BXs5Yjh1A==
-----END CERTIFICATE-----


-----BEGIN PRIVATE KEY-----
MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCnP8tNAAPMni4T
+Qs8NQbKycLZJP2x8LLkmkeNDAt5oTVMscZh1JcD46VVh8xKqqn1BYNFw4BKswsc
nfJIMUuDfLWlaubuiC9KylkUAAyjuAYp+3ILMoMX41g/35VGXCf1PPy1Mceqm4eK
MG3cPAqN+2rATCzgCRidt1dg/4UURScxUkKZb6xp0DUqW3xosSN5hSQBT/EUSBCk
7TBQPnD+cwbeFGWw8uOXgkc83uajnBNgzC+qGJxiJ8Trtfm9qiIvlRnWZiyORnPr
nhIm+IYtFIRanaW/EoPBsp5AgV8P44mc9JU1mgqI5+bW56pbLugqagND6rxAXSxd
n+nMKFjZAgMBAAECggEAU01A8y8otUer56LJZ8l5gpOPixwTiAc88wOs06HGLWSP
6Y06JAwA050VAk7K//2U/UcrpRnE0m/u/ufkfvSeifUZfDSMpQ4LkBLM31tO8FR+
LXbhzJXyGXnQwK+Z6BuKg2lG7PDIZD9m+Ps6HNGC6EjrWLAYQdYF/rgMu3FJVVhz
H02H501CURIV6T7wwKW+Sowtr3GKYRLHqk8mPPQlTl+u5Ix7hGJbffg8JMr4mqnT
IfBn2Js5tnyV9VqUtlylXtb/dv0nPz4OZj3PodluLHypZtiDhhzH3BOUpUw3hRse
8uh1OqHy6i3WLohwcdyA4Vb0CzMgqGncmqyLeAGtaQKBgQD1JAMSqhwZGdJ/NeC8
IZNkXi53lpu7frrRfjxl9sh/ERPVNdrW9bft9PBRzASfUgHZUIykOZnkZi0Q2gQb
M+CVxYF3q9jfWpfveFPOv1IZ1DlSEhwl5TV5Gn9gaV6WqIUM1IMs3iujz/8pvA3e
bIMak/BI9VO09h9rEzAYfgSgZwKBgQCuqHacVFy1EI52e1XL52KE7XyZSKA2QPuu
YgGMwJ65jNYwQmKO9bjJvrY05UKjQNTcMFAIGS6ggXF8GrmuGDcIuXkNZCC93ctd
kGqDZ1P7JZcPby++omnZUOtttH71gR6NvRNniHRNb8M7dn7EXqAcmJAdlAED59PW
/rr/Bgp0vwKBgQCy7iJAEWKI/8whhlYPfAJ1lDkemRP6+zmpiBbV5hfah8COM80U
ci5axhpT2jLVh6pJ0P2XMWQezmW+kyPEJ02ahJtHmmumPB4CcTXbBtMKf1mHiih5
kKBDURAAc2jTVxYmk0dcDJ6V51A8SkNlQpc/YxRUbJ04zR2JrmvAtNrzqQKBgF3P
zI2hXn4+m1aWXnn8GVWuvasTcEJT2xtKNuzkFR0ywWUAczG4V/28ut9keMt5Khvu
sz32UK4TeOIg9LTG+uBrxtJjgpaYx//EbwGPgXM3OJEotmtdjx8TsLCnLz4skT2u
XfrRNy7UpfeNaP+Ol89kPpw0f+saHRWwkr4h/4G/AoGBAKkMDr8rbunL/XmeFrQ/
n5Js6nB4ubXRwSrPnEh59irf4Q5fjeLMiZA7JOqOA+8qgEu75fQwzPjcRb6lVJ9t
YGdl6LlNe1NMkBCfUINiLnQWrfxVb4SGb+VdrGKX89BoEUO5GZ9T6S6Uoz0EktRV
7b5p71p80vgkl4e/Qz+YMqs0
-----END PRIVATE KEY-----
