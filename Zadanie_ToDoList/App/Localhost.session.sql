SELECT prod_id,
    ilosc,
    FROM ElementyZamowienia
WHERE ilosc = 100
UNION
SELECT prod_id,
    ilosc,
    FROM ElementyZamowienia
WHERE prod_id LIKE 'BNBG%';