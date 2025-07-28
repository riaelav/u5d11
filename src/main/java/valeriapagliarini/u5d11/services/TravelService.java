package valeriapagliarini.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import valeriapagliarini.u5d11.entities.Travel;
import valeriapagliarini.u5d11.enums.TravelStatus;
import valeriapagliarini.u5d11.exceptions.NotFoundException;
import valeriapagliarini.u5d11.payloads.TravelDTO;
import valeriapagliarini.u5d11.repositories.TravelRepository;

import java.util.List;

@Service
public class TravelService {

    @Autowired
    private TravelRepository travelRepository;

    public Travel save(TravelDTO payload) {
        Travel travel = new Travel(payload.destination(), payload.date());
        return this.travelRepository.save(travel);
    }

    public List<Travel> findAll() {
        return travelRepository.findAll();
    }

    public Travel findById(Long travelId) {
        return this.travelRepository.findById(travelId)
                .orElseThrow(() -> new NotFoundException(travelId));
    }

    public Travel findByIdAndUpdate(Long travelId, TravelDTO payload) {
        Travel found = this.findById(travelId);
        found.setDestination(payload.destination());
        found.setDate(payload.date());
        return this.travelRepository.save(found);
    }

    public void findByIdAndDelete(Long travelId) {
        Travel found = this.findById(travelId);
        this.travelRepository.delete(found);
    }

    public Travel updateStatus(Long travelId, TravelStatus status) {
        Travel found = this.findById(travelId);
        found.setStatus(status);
        return this.travelRepository.save(found);
    }
}
